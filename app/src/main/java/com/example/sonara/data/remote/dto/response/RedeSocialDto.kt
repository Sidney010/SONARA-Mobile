package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class RedeSocialDto(
    @SerializedName("id_redes_sociais") val id_redes_sociais: Int?,
    @SerializedName("link")             val link: String,
    @SerializedName("tipo_id")          val tipo_id: Int,
    @SerializedName("usuario_id")       val usuario_id: Int?
)