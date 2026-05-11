package com.example.sonara.domain.model

data class Usuario(
    val nome: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val dataNascimento: String,
    val fotoPerfil: String? = null
)