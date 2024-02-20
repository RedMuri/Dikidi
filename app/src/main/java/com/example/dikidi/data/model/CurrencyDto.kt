package com.example.dikidi.data.model

import com.example.dikidi.domain.model.Currency
import com.google.gson.annotations.SerializedName

data class CurrencyDto(
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