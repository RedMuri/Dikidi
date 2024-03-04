package com.example.feature_home

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.domain.model.ApiResponse
import com.example.domain.usecase.GetDataUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeStore : Store<HomeStore.Intent, HomeStore.State, HomeStore.Label> {

    sealed interface Intent {

        data object ClickRefresh : Intent

        data class ChangeSearchQuery(val query: String) : Intent
    }


    data class State(
        val searchQuery: String,
        val dataState: DataState,
    ) {

    }

    sealed interface DataState {

        data object Loading : DataState

        data class Content(val data: ApiResponse) : DataState

        data class Error(val error: Throwable) : DataState
    }


    sealed interface Label
}

class HomeStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getDataUseCase: GetDataUseCase,
) {

    fun create(): HomeStore =
        object : HomeStore, Store<HomeStore.Intent, HomeStore.State, HomeStore.Label> by storeFactory.create(
            name = "HomeStore",
            initialState = HomeStore.State(
                searchQuery = "",
                dataState = HomeStore.DataState.Loading
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        data class HomeContentLoaded(val content: ApiResponse) : Action

        data object HomeStartLoading : Action

        data class HomeLoadingError(val error: Throwable) : Action
    }

    private sealed interface Msg {

        data class ChangeSearchQuery(val query: String) : Msg

        data class HomeContentLoaded(val content: ApiResponse) : Msg

        data object HomeStartLoading : Msg

        data class HomeLoadingError(val error: Throwable) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getDataUseCase()
                    .onStart {
                        dispatch(Action.HomeStartLoading)
                    }
                    .catch {
                        dispatch(Action.HomeLoadingError(it))
                    }
                    .collect {
                        dispatch(Action.HomeContentLoaded(it))
                    }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<HomeStore.Intent, Action, HomeStore.State, Msg, HomeStore.Label>() {
        override fun executeIntent(intent: HomeStore.Intent, getState: () -> HomeStore.State) {
            when (intent) {
                is HomeStore.Intent.ClickRefresh -> {
                    scope.launch {
                        getDataUseCase()
                            .onStart {
                                dispatch(Msg.HomeStartLoading)
                                delay(1000)
                            }
                            .catch {
                                dispatch(Msg.HomeLoadingError(it))
                            }
                            .collect {
                                dispatch(Msg.HomeContentLoaded(it))
                            }
                    }
                }

                is HomeStore.Intent.ChangeSearchQuery -> {
                    dispatch(Msg.ChangeSearchQuery(intent.query))
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> HomeStore.State) {
            when (action) {
                is Action.HomeContentLoaded -> {
                    dispatch(Msg.HomeContentLoaded(action.content))
                }

                is Action.HomeLoadingError -> {
                    dispatch(Msg.HomeLoadingError(action.error))
                }

                Action.HomeStartLoading -> {
                    dispatch(Msg.HomeStartLoading)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<HomeStore.State, Msg> {
        override fun HomeStore.State.reduce(msg: Msg): HomeStore.State = when (msg) {
            is Msg.HomeContentLoaded -> copy(dataState = HomeStore.DataState.Content(msg.content))

            is Msg.HomeStartLoading -> copy(dataState = HomeStore.DataState.Loading)

            is Msg.HomeLoadingError -> copy(dataState = HomeStore.DataState.Error(msg.error))

            is Msg.ChangeSearchQuery -> copy(searchQuery = msg.query)
        }
    }
}
