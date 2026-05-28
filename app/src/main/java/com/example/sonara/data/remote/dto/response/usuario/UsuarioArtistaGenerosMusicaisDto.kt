package com.example.sonara.data.remote.dto.response.usuario

import com.google.gson.annotations.SerializedName

data class UsuarioArtistaGenerosMusicaisDto (
    @SerializedName("id") val id_genero_musical: Int,
    @SerializedName("nome") val nome: String
)