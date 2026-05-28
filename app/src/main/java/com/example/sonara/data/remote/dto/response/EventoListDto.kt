package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class EventoListDto(
    @SerializedName("Eventos") val eventos: List<EventoDto>
)