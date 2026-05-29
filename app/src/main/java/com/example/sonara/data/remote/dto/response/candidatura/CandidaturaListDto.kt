package com.example.sonara.data.remote.dto.response.candidatura

import com.google.gson.annotations.SerializedName

data class CandidaturaListDto(
    @SerializedName("candidaturas") val candidaturas: List<CandidaturaDto>
)