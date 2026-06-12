package com.example.sonara.data.remote.dto.response.candidatura

import com.google.gson.annotations.SerializedName

data class CandidaturaDto(
    @SerializedName("id_evento_artista") val id_evento_artista: Int,
    @SerializedName("artista_id") val artista_id: Int?,
    @SerializedName("evento_id") val evento_id: Int?,
    @SerializedName("cache_esperado") val cache_esperado: Double?,
    @SerializedName("cache_ofertado") val cache_ofertado: Double?,
    @SerializedName("cache_final") val cache_final: Double?,
    @SerializedName("contra_proposta") val contra_proposta: String?,
    @SerializedName("sobre_artista") val sobre_artista: String?,
    @SerializedName("motivo_inscricao") val motivo_inscricao: String?,
    @SerializedName("status") val status: String?,
    
    // Mantendo campos antigos como opcionais para evitar quebra em outras telas que listam candidaturas
    @SerializedName("artista_nome") val artista_nome: String? = null,
    @SerializedName("nome_artistico") val nome_artistico: String? = null,
    @SerializedName("descricao") val descricao: String? = null,
    @SerializedName("foto_url") val foto_url: String? = null,
    @SerializedName("status_id") val status_id: Int? = null,
    @SerializedName("status_nome") val status_nome: String? = null,
    @SerializedName("generos_musicais") val generos_musicais: String? = null,
    @SerializedName("telefone") val telefone: String? = null,
    @SerializedName("cidade") val cidade: String? = null
)
