package com.example.sonara.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class CandidaturaCreateRequestDto(
    @SerializedName("artista_id") val artista_id: Int,
    @SerializedName("evento_id") val evento_id: Int,
    @SerializedName("cache_ofertado") val cache_ofertado: Double?,
    @SerializedName("cache_esperado") val cache_esperado: Double?,
    @SerializedName("sobre_artista") val sobre_artista: String?,
    @SerializedName("motivo_inscricao") val motivo_inscricao: String?
)
