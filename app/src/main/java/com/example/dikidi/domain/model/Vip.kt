package com.example.dikidi.domain.model

import javax.inject.Inject

data class Vip @Inject constructor(
    val id: String,
    val image: Image,
    val name: String,
    val categories: List<String>,
    val award: String?,
    val vipTariff: Boolean
)