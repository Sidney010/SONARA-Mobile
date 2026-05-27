package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class CandidaturaDto(
    @SerializedName("id_candidatura")  val id_candidatura: Int,
    @SerializedName("evento_id")       val evento_id: Int,
    @SerializedName("artista_id")      val artista_id: Int,
    @SerializedName("artista_nome")    val artista_nome: String?,
    @SerializedName("nome_artistico")  val nome_artistico: String?,
    @SerializedName("descricao")       val descricao: String?,
    @SerializedName("foto_url")        val foto_url: String?,
    @SerializedName("cache_esperado")  val cache_esperado: Double?,
    @SerializedName("cache_ofertado")  val cache_ofertado: Double?,
    @SerializedName("status_id")       val status_id: Int?,
    @SerializedName("status_nome")     val status_nome: String?,
    @SerializedName("generos_musicais") val generos_musicais: String?,
    @SerializedName("telefone")        val telefone: String?,
    @SerializedName("cidade")          val cidade: String?
)