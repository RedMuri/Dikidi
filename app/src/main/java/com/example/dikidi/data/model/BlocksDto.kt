package com.example.dikidi.data.model

import com.google.gson.annotations.SerializedName

data class BlocksDto(
    @SerializedName("vip") val vip: List<VipDto>,
    @SerializedName("shares") val shares: SharesDto,
    @SerializedName("examples") val examples: String,
    @SerializedName("catalog") val catalog: List<CatalogDto>,
    @SerializedName("examples2") val examples2: String
)