package com.example.sonara.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class CreateUsuarioRequestDto(
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("senha") val senha: String,
    @SerializedName("cpf") val cpf: String,
    @SerializedName("data_nasc") val data_nasc: String,
    @SerializedName("nacionalidade_id") val nacionalidade_id: Int,
    @SerializedName("genero_id") val genero_id: Int,
    @SerializedName("criado") val criado: String,
    @SerializedName("ultima_atualizacao") val ultima_atualizacao: String,
    @SerializedName("telefone") val telefone: String,
    @SerializedName("tipo_usuario") val tipo_usuario: String,
    @SerializedName("nome_artistico") val nome_artistico: String? = null,
    @SerializedName("descricao") val descricao: String? = null,
    @SerializedName("cep") val cep: String,
    @SerializedName("cidade") val cidade: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("logradouro") val logradouro: String,
    @SerializedName("numero") val numero: String? = null,
    @SerializedName("complemento") val complemento: String? = null,
    @SerializedName("bairro") val bairro: String,
    @SerializedName("longitude") val longitude: String? = null,
    @SerializedName("latitude") val latitude: String? = null,
    @SerializedName("generos_musicais") val generos_musicais: List<Int>,
    @SerializedName("foto_perfil") val foto_perfil: String? = null
)

data class LoginRequestDto(
    @SerializedName("email") val email: String,
    @SerializedName("senha") val senha: String
)