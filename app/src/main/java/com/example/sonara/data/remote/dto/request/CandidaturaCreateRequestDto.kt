package com.example.sonara.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class CandidaturaCreateRequestDto(
    @SerializedName("evento_id")      val evento_id: Int,
    @SerializedName("artista_id")     val artista_id: Int,
    @SerializedName("cache_esperado") val cache_esperado: Double?,
    @SerializedName("status_id")      val status_id: Int = 1   // 1 = Pendente
)