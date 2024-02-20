package com.example.dikidi.data.model

import com.google.gson.annotations.SerializedName

data class DataDto(
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("catalog_count") val catalogCount: String,
    @SerializedName("blocks") val blocks: BlocksDto,
    @SerializedName("order") val order: List<String>
)