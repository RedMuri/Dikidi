package com.example.dikidi.data.repository

import com.example.dikidi.data.model.asEntity
import com.example.dikidi.data.network.ApiService
import com.example.dikidi.domain.model.ApiResponse
import com.example.dikidi.domain.repository.Repository
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