package com.example.sonara.data.remote.dto.response.usuario.perfil

import com.google.gson.annotations.SerializedName

data class UsuarioPerfilResponseDto(
    @SerializedName("status") val status: Boolean,
    @SerializedName("response") val usuario: UsuarioPerfilDto
)