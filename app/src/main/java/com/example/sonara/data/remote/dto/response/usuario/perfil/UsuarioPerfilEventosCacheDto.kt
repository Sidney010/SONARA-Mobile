package com.example.sonara.data.remote.dto.response.usuario.perfil

import com.google.gson.annotations.SerializedName

data class UsuarioPerfilEventosCacheDto(
    @SerializedName("cache_final")              val cacheFinal: Float?,
    @SerializedName("cache_esperado")           val cacheEsperado: Float?,
    @SerializedName("cache_ofertado")           val cacheOfertado: Float?,
    @SerializedName("contra_proposta")          val cacheProposta: Float?
)