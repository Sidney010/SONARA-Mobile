package com.example.sonara.data.remote.dto.response.evento

import com.google.gson.annotations.SerializedName

data class EventoDto(
    @SerializedName("id_evento") val idEvento: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("descricao") val descricao: String?,
    @SerializedName("local") val local: String?,
    @SerializedName("data") val data: String?,
    @SerializedName("hora_inicio") val horaInicio: String?,
    @SerializedName("hora_fim") val horaFim: String?,
    @SerializedName("endereco") val endereco: EnderecoDto?,
    @SerializedName("fotos") val fotos: List<FotoEventoDto> = emptyList(),
    @SerializedName("organizador") val organizador: OrganizadorDto?,
    @SerializedName("avaliacao") val avaliacao: AvaliacaoDto?,
    @SerializedName("artistas") val artistas: List<ArtistaEventoDto> = emptyList()
)

data class EnderecoDto(
    @SerializedName("cep") val cep: String?,
    @SerializedName("bairro") val bairro: String?,
    @SerializedName("cidade") val cidade: String?,
    @SerializedName("estado") val estado: String?,
    @SerializedName("numero") val numero: String?,
    @SerializedName("logradouro") val logradouro: String?,
    @SerializedName("complemento") val complemento: String?,
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?,
    @SerializedName("id_endereco_evento") val idEndereco: Int?
)

data class FotoEventoDto(
    @SerializedName("url") val url: String?,
    @SerializedName("id_foto") val idFoto: Int?
)

data class AvaliacaoDto(
    @SerializedName("media") val media: Double?,
    @SerializedName("total") val total: Int?
)

data class OrganizadorDto(
    @SerializedName("id_organizador") val idOrganizador: Int?,
    @SerializedName("nome") val nome: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("foto") val foto: String?
)

data class ArtistaEventoDto(
    @SerializedName("artista") val artista: ArtistaInfoDto?,
    @SerializedName("cache") val cache: CacheDto?,
    @SerializedName("informacoes") val informacoes: InformacoesArtistaDto?
)

data class ArtistaInfoDto(
    @SerializedName("id_usuario") val idUsuario: Int?,
    @SerializedName("nome_artistico") val nomeArtistico: String?,
    @SerializedName("foto") val foto: String?
)

data class CacheDto(
    @SerializedName("final") val final: Double?,
    @SerializedName("esperado") val esperado: Double?
)

data class InformacoesArtistaDto(
    @SerializedName("sobre_artista") val sobreArtista: String?
)
