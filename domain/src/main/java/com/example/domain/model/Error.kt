package com.example.dikidi.domain.model

import javax.inject.Inject

data class Error @Inject constructor(
    val code: Int,
    val message: String?
)