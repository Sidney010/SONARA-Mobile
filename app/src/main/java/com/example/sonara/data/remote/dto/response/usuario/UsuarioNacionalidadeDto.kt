package com.example.sonara.data.remote.dto.response.usuario

import com.google.gson.annotations.SerializedName

data class UsuarioNacionalidadeDto(
    @SerializedName("id_nacionalidade") val idNacionalidade: Int?,
    @SerializedName("nome")             val nome: String?
)