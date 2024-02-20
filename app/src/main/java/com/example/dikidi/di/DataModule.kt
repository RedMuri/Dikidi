package com.example.dikidi.di

import com.example.dikidi.data.network.ApiFactory
import com.example.dikidi.data.network.ApiService
import com.example.dikidi.data.repository.RepositoryImpl
import com.example.dikidi.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}