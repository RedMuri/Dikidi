package com.example.dikidi.data.model

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("thumb") val thumb: String,
    @SerializedName("origin") val origin: String
)