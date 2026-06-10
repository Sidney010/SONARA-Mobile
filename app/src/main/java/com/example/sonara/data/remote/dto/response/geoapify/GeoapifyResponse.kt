package com.example.sonara.data.remote.dto.response.geoapify

import com.google.gson.annotations.SerializedName

data class GeoapifyResponse(
    @SerializedName("results") val results: List<GeoapifyResult>
)

data class GeoapifyResult(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)
