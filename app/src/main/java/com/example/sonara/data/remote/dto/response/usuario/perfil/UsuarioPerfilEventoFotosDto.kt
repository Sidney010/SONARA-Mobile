package com.example.sonara.data.remote.dto.response.usuario.perfil

import com.google.gson.annotations.SerializedName

data class UsuarioPerfilEventoFotosDto(
    @SerializedName("id_foto") val idFoto: Int?,
    @SerializedName("url") val url: String?

)