package com.example.sonara.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class EventoDto(
    @SerializedName("id_evento")          val id_evento: Int,
    @SerializedName("evento_nome")        val evento_nome: String,
    @SerializedName("descricao")          val descricao: String?,
    @SerializedName("local")              val local: String?,
    @SerializedName("data")               val data: String?,
    @SerializedName("hora_inicio")        val hora_inicio: String?,
    @SerializedName("hora_fim")           val hora_fim: String?,
    @SerializedName("cep")                val cep: String?,
    @SerializedName("logradouro")         val logradouro: String?,
    @SerializedName("numero")             val numero: String?,
    @SerializedName("complemento")        val complemento: String?,
    @SerializedName("bairro")             val bairro: String?,
    @SerializedName("cidade")             val cidade: String?,
    @SerializedName("estado")             val estado: String?,
    @SerializedName("latitude")           val latitude: Double?,
    @SerializedName("longitude")          val longitude: Double?,
    @SerializedName("status_atual")       val status_atual: String?,
    @SerializedName("organizador_nome")   val organizador_nome: String?,
    @SerializedName("organizador_email")  val organizador_email: String?,
    @SerializedName("artista")            val artista: String?,
    @SerializedName("cache_final")        val cache_final: Double?,
    @SerializedName("sobre_artista")      val sobre_artista: String?,
    @SerializedName("media_avaliacao")    val media_avaliacao: Double?,
    @SerializedName("total_avaliacoes")   val total_avaliacoes: Int,
    @SerializedName("fotos")              val fotos: List<FotoDto> = emptyList()
)