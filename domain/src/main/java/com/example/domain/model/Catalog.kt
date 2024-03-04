package com.example.dikidi.domain.model

import javax.inject.Inject

data class Catalog @Inject constructor(
    val id: String,
    val name: String,
    val image: Image,
    val street: String,
    val house: String,
    val schedule: Any,
    val lat: String,
    val lng: String,
    val categories: List<Any>,
    val categoriesCatalog: List<Any>,
    val rating: Double,
    val isMaster: Boolean,
    val award: String?,
    val vipTariff: Boolean,
    val reviewCount: String,
    val backgrounds: List<String>,
    val currency: Currency,
    val masterId: String? = null
)