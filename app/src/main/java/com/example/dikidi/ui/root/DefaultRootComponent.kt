package com.example.dikidi.ui.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.dikidi.ui.home.DefaultHomeComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class DefaultRootComponent @AssistedInject constructor(
    private val homeComponentFactory: DefaultHomeComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config, componentContext: ComponentContext,
    ): RootComponent.Child {
        return when (config) {
            is Config.Home -> {
                val component = homeComponentFactory.create(
                    componentContext = componentContext
                )
                RootComponent.Child.Home(component)
            }

            Config.Catalog -> RootComponent.Child.Catalog
            Config.More -> RootComponent.Child.More
            Config.Notes -> RootComponent.Child.Notes
            Config.Sales -> RootComponent.Child.Sales
        }
    }

    override fun onHomeTabClicked() {
        navigation.bringToFront(Config.Home)
    }

    override fun onCatalogTabClicked() {
        navigation.bringToFront(Config.Catalog)
    }

    override fun onSalesTabClicked() {
        navigation.bringToFront(Config.Sales)
    }

    override fun onNotesTabClicked() {
        navigation.bringToFront(Config.Notes)
    }

    override fun onMoreTabClicked() {
        navigation.bringToFront(Config.More)
    }

    sealed interface Config : Parcelable {

        @Parcelize
        data object Home : Config
        @Parcelize
        data object Catalog : Config
        @Parcelize
        data object Sales : Config
        @Parcelize
        data object Notes : Config
        @Parcelize
        data object More : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultRootComponent
    }
}
