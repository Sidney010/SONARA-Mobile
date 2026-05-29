package com.example.sonara.data.remote.dto.response.redesocial

import com.google.gson.annotations.SerializedName

data class TipoRedeSocialDto(
    @SerializedName("id_tipo_redes_sociais") val id_tipo_redes_sociais: Int,
    @SerializedName("nome")                  val nome: String
)