package com.example.dikidi.domain.model

data class Data(
    val title: String,
    val image: String,
    val catalogCount: String,
    val blocks: Blocks,
    val order: List<String>
)