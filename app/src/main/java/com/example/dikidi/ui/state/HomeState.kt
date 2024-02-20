package com.example.dikidi.ui.state

import com.example.dikidi.domain.model.ApiResponse

sealed class HomeState {
    object Initial : HomeState()
    object Loading : HomeState()
    data class Content (val data: ApiResponse) : HomeState()
    data class Error (val error: Throwable) : HomeState()
}