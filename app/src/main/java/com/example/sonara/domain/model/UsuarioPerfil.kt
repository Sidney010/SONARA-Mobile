package com.example.sonara.domain.model

data class UsuarioPerfil(
    val id: Int,
    val nome: String,
    val email: String,
    val cpf: String?,
    val dataNasc: String?,
    val telefone: String?,
    val nacionalidadeId: Int?,
    val generoId: Int?,
    val tipoUsuario: String?,
    val nomeArtistico: String?,
    val descricao: String?,
    val criado: String?,
    val fotosUrls: List<String>
)
