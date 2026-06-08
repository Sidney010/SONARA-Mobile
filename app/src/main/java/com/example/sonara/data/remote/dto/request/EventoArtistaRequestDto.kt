package com.example.sonara.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class EventoArtistaRequestDto(
    @SerializedName("artista_id") val artistaId: Int,
    @SerializedName("evento_id") val eventoId: Int,
    @SerializedName("cache_esperado") val cacheEsperado: Double?,
    @SerializedName("cache_ofertado") val cacheOfertado: Double?,
    @SerializedName("cache_final") val cacheFinal: Double?,
    @SerializedName("contra_proposta") val contraProposta: Double?,
    @SerializedName("sobre_artista") val sobreArtista: String?,
    @SerializedName("motivo_inscricao") val motivoInscricao: String?
)