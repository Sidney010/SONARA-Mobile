package com.example.sonara.data.remote.dto.response.usuario.perfil

import com.google.gson.annotations.SerializedName

data class UsuarioPerfilEventoStatusDto(
    @SerializedName("id_status")   val idStatus: Int?,
    @SerializedName("nome")        val nome: String?,
    @SerializedName("descricao")   val descricao: String?
)