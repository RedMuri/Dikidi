package com.example.dikidi.data.model

import com.google.gson.annotations.SerializedName

data class ScheduleDto(
    @SerializedName("day") val day: String,
    @SerializedName("work_from") val workFrom: String,
    @SerializedName("work_to") val workTo: String
)
