package com.example.feature_home

import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {

    val model: StateFlow<HomeStore.State>

    fun onClickRefresh()

    fun changeSearchQuery(query: String)
}
