package com.example.dikidi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dikidi.data.network.ApiFactory
import com.example.dikidi.data.repository.RepositoryImpl
import com.example.dikidi.domain.usecase.GetDataUseCase
import com.example.dikidi.ui.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _homeScreenState =
        MutableStateFlow<HomeState>(HomeState.Initial)
    val homeScreenState = _homeScreenState.asStateFlow()

    val getDataUseCase = GetDataUseCase(RepositoryImpl(ApiFactory.apiService))


    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            getDataUseCase()
                .onStart {
                    _homeScreenState.emit(HomeState.Loading)
                }
                .catch {
                    _homeScreenState.emit(HomeState.Error(it))
                }.collect {
                    _homeScreenState.emit(HomeState.Content(it))
                }
        }
    }
}