package com.example.data.model

import com.example.domain.model.Shares
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class SharesDto @Inject constructor(
    @SerializedName("list") val list: List<ShareDto>,
    @SerializedName("count") val count: String
)

fun SharesDto.asEntity(): Shares {
    return Shares(
        list = list.map { it.asEntity() },
        count = count
    )
}