package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class UsuarioLoginDto(
    @SerializedName("id_usuario") val id_usuario: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("cpf") val cpf: String?,
    @SerializedName("data_nasc") val data_nasc: String?,
    @SerializedName("telefone") val telefone: String?,
    @SerializedName("nacionalidade_id") val nacionalidade_id: Int?,
    @SerializedName("genero_id") val genero_id: Int?
)