package com.example.dikidi.data.model

import com.example.dikidi.domain.model.Image
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class ImageDto @Inject constructor(
    @SerializedName("thumb") val thumb: String,
    @SerializedName("origin") val origin: String
)

fun ImageDto.asEntity(): Image {
    return Image(
        thumb = thumb,
        origin = origin
    )
}
