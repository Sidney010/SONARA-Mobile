package com.example.sonara.domain.model

data class UsuarioPerfil(
    val idUsuario: Int,
    val nome: String,
    val email: String,
    val cpf: String?,
    val dataNasc: String?,
    val telefone: String?,
    val foto: String?,
    val tipoUsuario: String,
    val genero: Genero?,
    val nacionalidade: Nacionalidade?,
    val endereco: Endereco?,
    val redesSociais: List<RedeSocial>
)