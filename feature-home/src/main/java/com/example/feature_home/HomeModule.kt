package com.example.feature_home

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.data.DataModule
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule::class])
interface HomeModule {

    @Binds
    fun componentFactory(impl: DefaultHomeComponent.Factory): HomeComponent.Factory

    companion object {

        @Provides
        fun provideStoreFactory(): StoreFactory = DefaultStoreFactory()
    }
}
