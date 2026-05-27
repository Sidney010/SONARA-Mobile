package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class FotoResponseDto(
    @SerializedName("evento_id") val evento_id: Int?,
    @SerializedName("foto")      val foto: String?,
    @SerializedName("id")        val id: Int?
)