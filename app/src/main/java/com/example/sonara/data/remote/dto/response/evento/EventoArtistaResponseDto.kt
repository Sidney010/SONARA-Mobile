package com.example.sonara.data.remote.dto.response.evento

import com.google.gson.annotations.SerializedName

data class EventoArtistaResponseDto(
    @SerializedName("status") val status: Boolean,
    @SerializedName("response") val response: EventoArtistaWrapperDto
)

data class EventoArtistaWrapperDto(
    @SerializedName("EventoArtista") val eventoArtista: EventoArtistaDto
)

data class EventoArtistaDto(
    @SerializedName("id_evento_artista") val idEventoArtista: Int,
    @SerializedName("artista_id") val artistaId: Int,
    @SerializedName("evento_id") val eventoId: Int,
    @SerializedName("cache_esperado") val cacheEsperado: String?,
    @SerializedName("cache_ofertado") val cacheOfertado: String?,
    @SerializedName("cache_final") val cacheFinal: String?,
    @SerializedName("contra_proposta") val contraProposta: String?,
    @SerializedName("sobre_artista") val sobreArtista: String?,
    @SerializedName("motivo_inscricao") val motivoInscricao: String?
)
