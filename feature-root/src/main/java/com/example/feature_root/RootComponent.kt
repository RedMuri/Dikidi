package com.example.feature_root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.dikidi.ui.home.HomeComponent

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
}
