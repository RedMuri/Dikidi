package com.example.dikidi.domain.model

import javax.inject.Inject

data class Image @Inject constructor(
    val thumb: String,
    val origin: String
)