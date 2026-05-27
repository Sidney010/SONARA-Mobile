package com.example.sonara.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class RedeSocialRequestDto(
    @SerializedName("link")       val link: String,
    @SerializedName("tipo_id")    val tipo_id: Int,
    @SerializedName("usuario_id") val usuario_id: Int
)