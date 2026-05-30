package com.example.sonara.data.remote.dto.response.usuario.perfil

import com.google.gson.annotations.SerializedName

data class UsuarioPerfilWrapperDto(
    @SerializedName("usuario") val usuario: UsuarioPerfilDto
)