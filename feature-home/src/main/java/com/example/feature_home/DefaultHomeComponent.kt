package com.example.feature_home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class DefaultHomeComponent @AssistedInject constructor(
    private val homeStoreFactory: HomeStoreFactory,
    @Assisted componentContext: ComponentContext,
) : HomeComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { homeStoreFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<HomeStore.State> = store.stateFlow

    override fun onClickRefresh() {
        store.accept(HomeStore.Intent.ClickRefresh)
    }

    override fun changeSearchQuery(query: String) {
        store.accept(HomeStore.Intent.ChangeSearchQuery(query))
    }

    @AssistedFactory
    interface Factory : HomeComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
        ): DefaultHomeComponent
    }
}
