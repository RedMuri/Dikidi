package com.example.dikidi.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponseDto(
    @SerializedName("error") val error: ErrorDto,
    @SerializedName("data") val data: DataDto
)