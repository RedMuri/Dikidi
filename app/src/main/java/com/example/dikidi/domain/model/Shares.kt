package com.example.dikidi.domain.model

import javax.inject.Inject

data class Shares @Inject constructor(
    val list: List<Share>,
    val count: String
)