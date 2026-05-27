package com.example.sonara.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class CandidaturaUpdateRequestDto(
    @SerializedName("status_id")      val status_id: Int,
    @SerializedName("cache_ofertado") val cache_ofertado: Double? = null
)