package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class GeneroMusicalListDto(
    @SerializedName("GeneroMusical") val generoMusical: List<GeneroMusicalDto>
)