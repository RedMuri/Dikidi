package com.example.dikidi.data.model

import com.example.dikidi.domain.model.Image
import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("thumb") val thumb: String,
    @SerializedName("origin") val origin: String
)

fun ImageDto.asEntity(): Image {
    return Image(
        thumb = thumb,
        origin = origin
    )
}
