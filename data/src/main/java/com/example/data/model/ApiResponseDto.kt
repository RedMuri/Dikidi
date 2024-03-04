package com.example.data.model

import com.example.domain.model.ApiResponse
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class ApiResponseDto @Inject constructor(
    @SerializedName("error") val error: ErrorDto,
    @SerializedName("data") val data: DataDto
)

fun ApiResponseDto.asEntity(): ApiResponse {
    return ApiResponse(
        error = error.asEntity(),
        data = data.asEntity()
    )
}