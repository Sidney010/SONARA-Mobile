package com.example.sonara.data.remote.dto.response.usuario.perfil

import com.google.gson.annotations.SerializedName

data class UsuarioPerfilEventosEnderecoDto (
    @SerializedName("cep")              val cep: String?,
    @SerializedName("bairro")           val bairro: String?,
    @SerializedName("cidade")           val cidade: String?,
    @SerializedName("estado")           val estado: String?,
    @SerializedName("numero")           val numero: String?,
    @SerializedName("latitude")         val latitude: String?,
    @SerializedName("longitude")        val longitude: String?,
    @SerializedName("logradouro")       val logradouro: String?,
    @SerializedName("complemento")      val complemento: String?,
    @SerializedName("id_endereco_evento") val idEnderecoEvento: Int?
)