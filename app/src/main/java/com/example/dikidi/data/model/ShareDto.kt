package com.example.dikidi.data.model

import com.google.gson.annotations.SerializedName

data class ShareDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("time_start") val timeStart: String,
    @SerializedName("time_stop") val timeStop: String,
    @SerializedName("public_time_start") val publicTimeStart: String,
    @SerializedName("public_time_stop") val publicTimeStop: String,
    @SerializedName("discount_value") val discountValue: String,
    @SerializedName("view") val view: String,
    @SerializedName("used_count") val usedCount: String,
    @SerializedName("company_id") val companyId: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("company_street") val companyStreet: String,
    @SerializedName("company_house") val companyHouse: String,
    @SerializedName("company_image") val companyImage: String
)