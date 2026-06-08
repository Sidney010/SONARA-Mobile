package com.example.sonara.domain.model

data class EventoArtista(
    val idEventoArtista: Int,
    val artistaId: Int,
    val eventoId: Int,
    val cacheEsperado: Double?,
    val cacheOfertado: Double?,
    val cacheFinal: Double?,
    val contraProposta: Double?,
    val sobreArtista: String?,
    val motivoInscricao: String?
)