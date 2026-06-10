package com.example.sonara.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class EventoCreateRequestDto(
    @SerializedName("nome")           val evento_nome: String,
    @SerializedName("descricao")      val descricao: String?,
    @SerializedName("local")          val local: String?,
    @SerializedName("data")           val data: String?,
    @SerializedName("hora_inicio")    val hora_inicio: String?,
    @SerializedName("hora_fim")       val hora_fim: String?,
    @SerializedName("cep")            val cep: String?,
    @SerializedName("logradouro")     val logradouro: String?,
    @SerializedName("numero")         val numero: String?,
    @SerializedName("complemento")    val complemento: String?,
    @SerializedName("bairro")         val bairro: String?,
    @SerializedName("cidade")         val cidade: String?,
    @SerializedName("estado")         val estado: String?,
    @SerializedName("organizador_id") val organizador_id: Int,
    @SerializedName("latitude")       val latitude: Double? = null,
    @SerializedName("longitude")      val longitude: Double? = null
)