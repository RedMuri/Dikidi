package com.example.dikidi.ui.root

import android.os.Parcelable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.value.Value
import com.example.dikidi.ui.home.HomeComponent
import kotlinx.parcelize.Parcelize

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    val navigation: StackNavigation<Config>

    sealed interface Child {

        data class Home(val component: HomeComponent) : Child

        data object Catalog : Child
        data object Sales : Child
        data object Notes : Child
        data object More : Child
    }

    sealed interface Config : Parcelable {

        @Parcelize data object Home : Config

        @Parcelize data object Catalog : Config
        @Parcelize data object Sales : Config
        @Parcelize data object Notes : Config
        @Parcelize data object More : Config
    }
}