package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class EventoListDto(
    @SerializedName("eventos") val eventos: List<EventoDto>
)