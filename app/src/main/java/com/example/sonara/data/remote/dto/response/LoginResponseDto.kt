package com.example.sonara.data.remote.dto.response

import com.example.sonara.data.remote.dto.response.login.UsuarioLoginDto
import com.google.gson.annotations.SerializedName

// Login response - a API retorna diretamente no body (não dentro de "response")
data class LoginResponseDto(
    @SerializedName("status") val status: Boolean,
    @SerializedName("token") val token: String,
    @SerializedName("usuario") val usuario: UsuarioLoginDto
)