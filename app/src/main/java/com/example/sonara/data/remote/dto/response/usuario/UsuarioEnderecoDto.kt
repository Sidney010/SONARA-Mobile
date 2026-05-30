package com.example.sonara.data.remote.dto.response.usuario

import com.google.gson.annotations.SerializedName

data class UsuarioEnderecoDto(
    @SerializedName("id_endereco")      val idEndereco: Int?,
    @SerializedName("cep")              val cep: String?,
    @SerializedName("cidade")           val cidade: String?,
    @SerializedName("estado")           val estado: String?,
    @SerializedName("logradouro")       val logradouro: String?,
    @SerializedName("numero")           val numero: String?,
    @SerializedName("complemento")      val complemento: String?,
    @SerializedName("bairro")           val bairro: String?,
    @SerializedName("latitude")         val latitude: String?,
    @SerializedName("longitude")        val longitude: String?
)