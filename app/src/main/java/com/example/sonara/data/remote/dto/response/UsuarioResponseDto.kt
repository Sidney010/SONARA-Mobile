package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class UsuarioResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("cpf")
    val cpf: String,

    @SerializedName("data_nasc")
    val dataNascimento: String, // Formato yyyy-MM-dd [cite: 29]

    @SerializedName("endereco_id")
    val enderecoId: Int?,

    @SerializedName("foto_perfil")
    val fotoPerfil: String? // URL ou Base64 da imagem [cite: 29]
)