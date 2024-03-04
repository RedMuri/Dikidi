package com.example.data.model

import com.example.domain.model.Currency
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class CurrencyDto @Inject constructor(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("abbr") val abbr: String
)

fun CurrencyDto.asEntity(): Currency {
    return Currency(
        id = id,
        title = title,
        abbr = abbr
    )
}