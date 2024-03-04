package com.example.dikidi.data.network

import com.example.dikidi.data.model.ApiResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("home/info")
    suspend fun getData(@Header("Authorization") token: String = API_KEY): ApiResponseDto

    @POST("home/info/{city_id}")
    suspend fun getDataByCity(
        @Header("Authorization") token: String = API_KEY,
        @Path("city_id") cityId: Int = DEFAULT_CITY_ID,
    ): ApiResponseDto

    companion object {

        private const val API_KEY = "maJ9RyT4TJLt7bmvYXU7M3h4F797fUKofUf3373foN94q4peAM"
        private const val DEFAULT_CITY_ID = 468902
    }
}