package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

// Catalogs
data class NacionalidadeDto(
    @SerializedName("id_nacionalidade") val id_nacionalidade: Int,
    @SerializedName("nome") val nome: String
)