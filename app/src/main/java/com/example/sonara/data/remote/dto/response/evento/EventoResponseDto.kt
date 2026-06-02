package com.example.sonara.data.remote.dto.response.evento

import com.google.gson.annotations.SerializedName

data class EventoResponseDto(
    @SerializedName("evento") val evento: EventoDto
)
