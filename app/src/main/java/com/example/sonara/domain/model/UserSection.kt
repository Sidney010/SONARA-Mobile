package com.example.sonara.domain.model

data class UserSession(
    val idUsuario: Int,
    val nome: String,
    val email: String,
    val foto: String?,
    val tipoUsuario: String,
    val idArtista: Int?,
    val idOrganizador: Int?
)