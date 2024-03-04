package com.example.domain.model

import com.example.domain.model.Share
import javax.inject.Inject

data class Shares @Inject constructor(
    val list: List<Share>,
    val count: String
)