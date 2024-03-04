package com.example.dikidi.domain.model

import javax.inject.Inject

data class ApiResponse @Inject constructor(
    val error: Error,
    val data: Data
)