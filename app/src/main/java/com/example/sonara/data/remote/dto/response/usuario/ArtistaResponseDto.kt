package com.example.sonara.data.remote.dto.response.usuario

import com.google.gson.annotations.SerializedName

data class ArtistaListDto(
    @SerializedName("Artista") val artistas: List<ArtistaDto>
)

data class ArtistaDto(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("cpf") val cpf: String,
    @SerializedName("data_nasc") val dataNasc: String?,
    @SerializedName("telefone") val telefone: String?,
    @SerializedName("foto") val foto: String?,
    @SerializedName("criado") val criado: String?,
    @SerializedName("ultima_atualizacao") val ultimaAtualizacao: String?,
    @SerializedName("genero") val genero: String?,
    @SerializedName("nacionalidade") val nacionalidade: String?,
    @SerializedName("cep") val cep: String?,
    @SerializedName("logradouro") val logradouro: String?,
    @SerializedName("numero") val numero: String?,
    @SerializedName("complemento") val complemento: String?,
    @SerializedName("bairro") val bairro: String?,
    @SerializedName("cidade") val cidade: String?,
    @SerializedName("estado") val estado: String?,
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("rede_social_link") val redeSocialLink: String?,
    @SerializedName("rede_social_tipo") val redeSocialTipo: String?,
    @SerializedName("nome_artistico") val nomeArtistico: String?,
    @SerializedName("sobre_artista") val sobreArtista: String?,
    @SerializedName("artista_id") val artistaId: Int,
    @SerializedName("tipo_usuario") val tipoUsuario: String?
)
