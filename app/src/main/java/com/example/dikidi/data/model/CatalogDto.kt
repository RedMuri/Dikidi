package com.example.dikidi.data.model

import com.google.gson.annotations.SerializedName

data class CatalogDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: ImageDto,
    @SerializedName("street") val street: String,
    @SerializedName("house") val house: String,
    @SerializedName("schedule") val schedule: Any,
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String,
    @SerializedName("categories") val categories: List<Any>,
    @SerializedName("categories_catalog") val categoriesCatalog: List<Any>,
    @SerializedName("rating") val rating: Int,
    @SerializedName("isMaster") val isMaster: Boolean,
    @SerializedName("award") val award: String?,
    @SerializedName("vip_tariff") val vipTariff: Boolean,
    @SerializedName("reviewCount") val reviewCount: String,
    @SerializedName("backgrounds") val backgrounds: List<String>,
    @SerializedName("currency") val currency: CurrencyDto,
    @SerializedName("master_id") val masterId: String? = null
)