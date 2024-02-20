package com.example.dikidi.domain.model

data class Vip(
    val id: String,
    val image: Image,
    val name: String,
    val categories: List<String>,
    val award: String?,
    val vipTariff: Boolean
)