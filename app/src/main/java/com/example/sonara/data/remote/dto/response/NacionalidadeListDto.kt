package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

// Wrapper para listas
data class NacionalidadeListDto(
    @SerializedName("nacionalidades") val nacionalidades: List<NacionalidadeDto>
)