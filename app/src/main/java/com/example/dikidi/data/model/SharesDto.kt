package com.example.dikidi.data.model

import com.example.dikidi.domain.model.Shares
import com.google.gson.annotations.SerializedName

data class SharesDto(
    @SerializedName("list") val list: List<ShareDto>,
    @SerializedName("count") val count: String
)

fun SharesDto.asEntity(): Shares {
    return Shares(
        list = list.map { it.asEntity() },
        count = count
    )
}