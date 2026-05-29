package com.example.sonara.data.remote.dto.response.redesocial

import com.google.gson.annotations.SerializedName

data class TipoRedeSocialListDto(
    @SerializedName("TipoRedesSociais") val tipoRedesSociais: List<TipoRedeSocialDto>
)