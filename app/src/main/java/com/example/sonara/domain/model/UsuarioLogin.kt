package com.example.sonara.domain.model

data class UsuarioLogin(
    val idUsuario: Int,
    val nome: String,
    val email: String,
    val foto: String? = null,
    val tipoUsuario: String, // "Artista" ou "Organizador" ou "Usuario"
    val idArtista: Int?,
    val idOrganizador: Int?
)