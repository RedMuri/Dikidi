package com.example.data.model

import com.example.domain.model.Error
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class ErrorDto @Inject constructor(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String?
)

fun ErrorDto.asEntity(): Error {
    return Error(
        code = code,
        message = message
    )
}