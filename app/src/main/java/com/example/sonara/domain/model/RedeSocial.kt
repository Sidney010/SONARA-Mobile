package com.example.sonara.domain.model

data class RedeSocial(
    val id: Int?       = null,
    val link: String,
    val tipoId: Int,
    val tipoNome: String? = null,
    val usuarioId: Int?   = null
)