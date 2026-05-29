package com.example.sonara.data.remote.dto.response.nacionalidade

import com.google.gson.annotations.SerializedName

// Wrapper para listas
data class NacionalidadeListDto(
    @SerializedName("nacionalidades") val nacionalidades: List<NacionalidadeDto>
)