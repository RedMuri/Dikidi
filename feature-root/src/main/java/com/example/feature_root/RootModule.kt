package com.example.feature_root

import com.example.feature_home.HomeModule
import dagger.Binds
import dagger.Module

@Module(includes = [HomeModule::class])
interface RootModule {

    @Binds
    fun componentFactory(impl: DefaultRootComponent.Factory): RootComponent.Factory
}
