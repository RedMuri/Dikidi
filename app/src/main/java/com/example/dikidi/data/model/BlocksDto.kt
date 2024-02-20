package com.example.dikidi.data.model

import com.example.dikidi.domain.model.Blocks
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class BlocksDto @Inject constructor(
    @SerializedName("vip") val vip: List<VipDto>,
    @SerializedName("shares") val shares: SharesDto,
    @SerializedName("examples") val examples: String,
    @SerializedName("catalog") val catalog: List<CatalogDto>,
    @SerializedName("examples2") val examples2: String
)

fun BlocksDto.asEntity(): Blocks {
    return Blocks(
        vip = vip.map { it.asEntity() },
        shares = shares.asEntity(),
        examples = examples,
        catalog = catalog.map { it.asEntity() },
        examples2 = examples2
    )
}