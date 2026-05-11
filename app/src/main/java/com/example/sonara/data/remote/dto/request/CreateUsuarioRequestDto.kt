package com.example.sonara.data.remote.dto.request

data class CreateUsuarioRequestDto(
    val nome: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val data_nasc: String,
    val foto_perfil: String? = null
)