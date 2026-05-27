package com.example.sonara.domain.model

data class Candidatura(
    val id: Int,
    val eventoId: Int,
    val artistaId: Int,
    val artistaNome: String?,
    val nomeArtistico: String?,
    val descricao: String?,
    val fotoUrl: String?,
    val cacheEsperado: Double?,
    val cacheOfertado: Double?,
    val statusId: Int?,
    val statusNome: String?,
    val generosMusicais: String?,
    val telefone: String?,
    val cidade: String?
)