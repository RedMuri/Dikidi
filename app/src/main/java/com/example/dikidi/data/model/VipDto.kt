package com.example.dikidi.data.model

import com.google.gson.annotations.SerializedName

data class VipDto(
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: ImageDto,
    @SerializedName("name") val name: String,
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("award") val award: String?,
    @SerializedName("vip_tariff") val vipTariff: Boolean
)