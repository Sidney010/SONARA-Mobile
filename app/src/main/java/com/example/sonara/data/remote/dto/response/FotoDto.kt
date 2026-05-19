package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class FotoDto(
    @SerializedName("id_foto") val id_foto: Int?,
    @SerializedName("caminho") val caminho: String?
)