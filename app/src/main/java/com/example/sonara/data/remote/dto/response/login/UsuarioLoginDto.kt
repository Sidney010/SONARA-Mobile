package com.example.sonara.data.remote.dto.response.login

import com.google.gson.annotations.SerializedName

data class UsuarioLoginDto(
    @SerializedName("id_usuario")       val idUsuario: Int,
    @SerializedName("nome")             val nome: String,
    @SerializedName("email")            val email: String,
    @SerializedName("foto")             val foto: String?,
    @SerializedName("tipo_usuario")     val tipoUsuario: String, // "Artista" ou "Organizador" ou "Usuario"
    @SerializedName("id_artista")       val idArtista: Int?,
    @SerializedName("id_organizador")   val idOrganizador: Int?
)