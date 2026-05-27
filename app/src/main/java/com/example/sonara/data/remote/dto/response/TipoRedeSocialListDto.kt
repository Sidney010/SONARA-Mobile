package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class TipoRedeSocialListDto(
    @SerializedName("TipoRedesSociais") val tipoRedesSociais: List<TipoRedeSocialDto>
)