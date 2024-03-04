package com.example.data.model

import com.example.domain.model.Data
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class DataDto @Inject constructor(
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("catalog_count") val catalogCount: String,
    @SerializedName("blocks") val blocks: BlocksDto,
    @SerializedName("order") val order: List<String>
)

fun DataDto.asEntity(): Data {
    return Data(
        title = title,
        image = image,
        catalogCount = catalogCount,
        blocks = blocks.asEntity(),
        order = order
    )
}