package com.example.dikidi.data.model

import com.example.dikidi.domain.model.Catalog
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class CatalogDto @Inject constructor(
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
    @SerializedName("rating") val rating: Double,
    @SerializedName("isMaster") val isMaster: Boolean,
    @SerializedName("award") val award: String?,
    @SerializedName("vip_tariff") val vipTariff: Boolean,
    @SerializedName("reviewCount") val reviewCount: String,
    @SerializedName("backgrounds") val backgrounds: List<String>,
    @SerializedName("currency") val currency: CurrencyDto,
    @SerializedName("master_id") val masterId: String? = null
)

fun CatalogDto.asEntity(): Catalog {
    return Catalog(
        id = id,
        name = name,
        image = image.asEntity(),
        street = street,
        house = house,
        schedule = schedule,
        lat = lat,
        lng = lng,
        categories = categories,
        categoriesCatalog = categoriesCatalog,
        rating = rating,
        isMaster = isMaster,
        award = award,
        vipTariff = vipTariff,
        reviewCount = reviewCount,
        backgrounds = backgrounds,
        currency = currency.asEntity(),
        masterId = masterId
    )
}