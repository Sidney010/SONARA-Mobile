package com.example.sonara.data.remote.dto.response.redesocial

import com.google.gson.annotations.SerializedName

data class RedeSocialDto(
    @SerializedName("id")         val idRedesSociais: Int?,
    @SerializedName("link")       val link: String,
    @SerializedName("tipo")       val tipo: String?,       // <- existe na API!
    @SerializedName("tipo_id")    val tipoId: Int,
    @SerializedName("usuario_id") val usuarioId: Int?
)