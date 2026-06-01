package com.example.sonara.data.remote.dto.response.foto

import com.google.gson.annotations.SerializedName

data class FotoDto(
    @SerializedName("id_foto") val idFoto: Int?,
    @SerializedName("url") val url: String?
)