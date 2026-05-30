package com.example.sonara.data.remote.dto.response.usuario.perfil

import com.google.gson.annotations.SerializedName

data class UsuarioPerfilOrganizadorDto(
    @SerializedName("id_organizador") val idOrganizador: Int,
    @SerializedName("eventos")        val eventos: List<UsuarioPerfilEventosDto>
)