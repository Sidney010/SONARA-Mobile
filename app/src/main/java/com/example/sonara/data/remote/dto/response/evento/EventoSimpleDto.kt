package com.example.sonara.data.remote.dto.response.evento

import com.google.gson.annotations.SerializedName

data class EventoSimpleDto(
    @SerializedName("id_evento")    val id_evento: Int,
    @SerializedName("evento_nome")  val evento_nome: String
)