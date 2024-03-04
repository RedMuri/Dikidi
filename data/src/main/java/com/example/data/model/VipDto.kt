package com.example.data.model

import com.example.domain.model.Vip
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class VipDto @Inject constructor(
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: ImageDto,
    @SerializedName("name") val name: String,
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("award") val award: String?,
    @SerializedName("vip_tariff") val vipTariff: Boolean
)

fun VipDto.asEntity(): Vip {
    return Vip(
        id = id,
        image = image.asEntity(),
        name = name,
        categories = categories,
        award = award,
        vipTariff = vipTariff
    )
}