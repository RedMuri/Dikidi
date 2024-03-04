package com.example.dikidi.domain.model

import javax.inject.Inject

data class Blocks @Inject constructor(
    val vip: List<Vip>,
    val shares: Shares,
    val examples: String,
    val catalog: List<Catalog>,
    val examples2: String
)