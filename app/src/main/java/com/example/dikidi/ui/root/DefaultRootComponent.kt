package com.example.dikidi.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.dikidi.ui.home.DefaultHomeComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DefaultRootComponent @AssistedInject constructor(
    private val homeComponentFactory: DefaultHomeComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    override val navigation = StackNavigation<RootComponent.Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = RootComponent.Config.Home,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: RootComponent.Config, componentContext: ComponentContext,
    ): RootComponent.Child {
        return when (config) {
            is RootComponent.Config.Home -> {
                val component = homeComponentFactory.create(
                    componentContext = componentContext
                )
                RootComponent.Child.Home(component)
            }

            RootComponent.Config.Catalog -> RootComponent.Child.Catalog
            RootComponent.Config.More -> RootComponent.Child.More
            RootComponent.Config.Notes -> RootComponent.Child.Notes
            RootComponent.Config.Sales -> RootComponent.Child.Sales
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultRootComponent
    }
}
