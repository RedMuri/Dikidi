package com.example.feature_home

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {

    val model: StateFlow<HomeStore.State>

    fun onClickRefresh()

    fun changeSearchQuery(query: String)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
        ): HomeComponent
    }
}
