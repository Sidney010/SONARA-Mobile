package com.example.sonara.data.remote.dto.response.usuario

import com.google.gson.annotations.SerializedName

data class UsuarioRedesSociaisDto (
    @SerializedName("id")       val id: Int?,
    @SerializedName("link")     val link: String?,
    @SerializedName("tipo")     val tipo: String?,
    @SerializedName("tipo_id")  val tipo_id: Int?
)