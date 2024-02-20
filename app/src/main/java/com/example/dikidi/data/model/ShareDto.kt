package com.example.dikidi.data.model

import com.example.dikidi.domain.model.Share
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

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

fun ShareDto.asEntity(): Share {
    return Share(
        id = id,
        name = name,
        timeStart = timeStart,
        timeStop = timeStop,
        publicTimeStart = publicTimeStart,
        publicTimeStop = timestampToString(publicTimeStop),
        discountValue = discountValue,
        view = view,
        usedCount = usedCount,
        companyId = companyId,
        icon = icon,
        companyName = companyName,
        companyStreet = companyStreet,
        companyHouse = companyHouse,
        companyImage = companyImage
    )
}

private fun timestampToString(timestamp: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d MMMM", Locale("ru", "RU")) // "ru" for Russian locale

    val date = inputFormat.parse(timestamp)
    return outputFormat.format(date?:timestamp)
}