package com.example.sonara.data.remote.dto.response.usuario

import com.google.gson.annotations.SerializedName

data class UsuarioGeneroDto(
    @SerializedName("id_genero") val id_genero: Int?,
    @SerializedName("nome")      val nome: String?
)