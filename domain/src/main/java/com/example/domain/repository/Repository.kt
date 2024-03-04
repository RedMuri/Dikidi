package com.example.dikidi.domain.repository

import com.example.dikidi.domain.model.ApiResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getData(): Flow<ApiResponse>
}