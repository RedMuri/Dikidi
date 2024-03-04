package com.example.domain.repository

import com.example.domain.model.ApiResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getData(): Flow<ApiResponse>
}