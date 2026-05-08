package com.example.sonara.data.remote.dto

data class UsuarioRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val data_nasc: String, // "yyyy-MM-dd" conforme Swagger [cite: 29]
    val foto_perfil: String? = null
)