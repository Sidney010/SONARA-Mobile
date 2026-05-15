package com.example.sonara.domain.model

enum class UserType(val apiValue: String) {
    ARTISTA("artista"),
    ORGANIZADOR("organizador"),
    USUARIO_COMUM("usuario")
}

fun UserType.toDisplayName(): String {
    return when (this) {
        UserType.ARTISTA -> "Artista"
        UserType.ORGANIZADOR -> "Organizador"
        UserType.USUARIO_COMUM -> "Usuário comum"
    }
}