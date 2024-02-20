package com.example.dikidi.data.model

import com.example.dikidi.domain.model.ApiResponse
import com.google.gson.annotations.SerializedName

data class ApiResponseDto(
    @SerializedName("error") val error: ErrorDto,
    @SerializedName("data") val data: DataDto
)

fun ApiResponseDto.asEntity(): ApiResponse {
    return ApiResponse(
        error = error.asEntity(),
        data = data.asEntity()
    )
}