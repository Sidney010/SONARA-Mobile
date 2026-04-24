package com.example.sonara.domain.model

enum class UserType {
    ARTISTA,
    ORGANIZADOR,
    USUARIO_COMUM
}
fun UserType.toDisplayName(): String {
    return when (this) {
        UserType.ARTISTA -> "Artista"
        UserType.ORGANIZADOR -> "Organizador"
        UserType.USUARIO_COMUM -> "Usuário comum"
    }
}