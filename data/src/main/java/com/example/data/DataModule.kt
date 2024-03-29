package com.example.data

import com.example.data.network.ApiFactory
import com.example.data.network.ApiService
import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
