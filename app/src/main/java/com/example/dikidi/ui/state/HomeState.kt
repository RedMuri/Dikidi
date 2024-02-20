package com.example.dikidi.ui.state

import com.example.dikidi.domain.model.ApiResponse
import javax.inject.Inject

sealed class HomeState {
    data object Loading : HomeState()
    data class Content @Inject constructor(val data: ApiResponse) : HomeState()
    data class Error @Inject constructor(val error: Throwable) : HomeState()
}