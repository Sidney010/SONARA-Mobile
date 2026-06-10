package com.example.sonara.data.remote.api.geoapify

import com.example.sonara.data.remote.dto.response.geoapify.GeoapifyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoapifyApiService {
    @GET("v1/geocode/search")
    suspend fun buscarCoordenadas(
        @Query("text") text: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 1,
        @Query("apiKey") apiKey: String
    ): GeoapifyResponse
}
