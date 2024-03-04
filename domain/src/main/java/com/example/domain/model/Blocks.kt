package com.example.domain.model

import com.example.dikidi.domain.model.Catalog
import com.example.dikidi.domain.model.Shares
import com.example.dikidi.domain.model.Vip
import javax.inject.Inject

data class Blocks @Inject constructor(
    val vip: List<Vip>,
    val shares: Shares,
    val examples: String,
    val catalog: List<Catalog>,
    val examples2: String
)