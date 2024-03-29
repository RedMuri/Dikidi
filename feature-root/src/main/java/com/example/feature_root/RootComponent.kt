package com.example.feature_root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.feature_home.HomeComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onHomeTabClicked()
    fun onCatalogTabClicked()
    fun onSalesTabClicked()
    fun onNotesTabClicked()
    fun onMoreTabClicked()

    sealed interface Child {

        data class Home(val component: HomeComponent) : Child
        data object Catalog : Child
        data object Sales : Child
        data object Notes : Child
        data object More : Child
    }

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): RootComponent
    }
}
