package com.example.dikidi.data.model

import com.google.gson.annotations.SerializedName

data class SharesDto(
    @SerializedName("list") val list: List<ShareDto>,
    @SerializedName("count") val count: String
)