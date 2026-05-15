package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class GeneroMusicalDto(
    @SerializedName("id_genero_musical") val id_genero_musical: Int,
    @SerializedName("nome") val nome: String
)