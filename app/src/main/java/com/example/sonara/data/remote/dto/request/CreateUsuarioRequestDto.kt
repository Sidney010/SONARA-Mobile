package com.example.sonara.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class CreateUsuarioRequestDto(

    @SerializedName("nome")
    val nome: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("senha")
    val senha: String,

    @SerializedName("cpf")
    val cpf: String,

    @SerializedName("data_nasc")
    val data_nasc: String,

    @SerializedName("foto_perfil")
    val foto_perfil: String? = null
)