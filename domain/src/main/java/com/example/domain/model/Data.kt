package com.example.dikidi.domain.model

import com.example.domain.model.Blocks
import javax.inject.Inject

data class Data @Inject constructor(
    val title: String,
    val image: String,
    val catalogCount: String,
    val blocks: Blocks,
    val order: List<String>
)