package com.example.dikidi.data.repository

import com.example.dikidi.data.model.asEntity
import com.example.dikidi.data.network.ApiService
import com.example.dikidi.domain.model.ApiResponse
import com.example.dikidi.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val apiService: ApiService
): Repository {

    override fun getData(): Flow<ApiResponse> = flow {
        emit(apiService.getData().asEntity())
    }
}