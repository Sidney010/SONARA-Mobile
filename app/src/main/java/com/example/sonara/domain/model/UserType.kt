package com.example.sonara.domain.model

enum class UserType(val apiValue: String) {
    ARTISTA("Artista"),
    ORGANIZADOR("Organizador"),
    USUARIO_COMUM("Usuario")
}

fun UserType.toDisplayName(): String {
    return when (this) {
        UserType.ARTISTA -> "Artista"
        UserType.ORGANIZADOR -> "Organizador"
        UserType.USUARIO_COMUM -> "Usuário comum"
    }
}