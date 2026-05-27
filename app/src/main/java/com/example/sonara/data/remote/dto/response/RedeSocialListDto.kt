package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class RedeSocialListDto(
    @SerializedName("RedesSociais") val redesSociais: List<RedeSocialDto>
)
