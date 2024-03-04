package com.example.data.repository

import com.example.data.model.asEntity
import com.example.data.network.ApiService
import com.example.domain.model.ApiResponse
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
): Repository {

    override fun getData(): Flow<ApiResponse> = flow {
        emit(apiService.getDataByCity().asEntity())
    }
}