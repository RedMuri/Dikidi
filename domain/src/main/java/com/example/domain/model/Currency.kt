package com.example.domain.model

import javax.inject.Inject

data class Currency @Inject constructor(
    val id: String,
    val title: String,
    val abbr: String
)

