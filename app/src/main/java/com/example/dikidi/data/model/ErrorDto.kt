package com.example.dikidi.data.model

import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String?
)

fun ErrorDto.asEntity(): com.example.dikidi.domain.model.Error {
    return com.example.dikidi.domain.model.Error(
        code = code,
        message = message
    )
}