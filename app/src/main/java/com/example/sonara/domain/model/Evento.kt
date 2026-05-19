package com.example.sonara.domain.model


data class Evento(
    val id: Int,
    val nome: String,
    val descricao: String?,
    val local: String?,
    val data: String?,                   // ISO: "2026-06-20T00:00:00.000Z"
    val horaInicio: String?,             // "09:00:00"
    val horaFim: String?,
    val cep: String?,
    val logradouro: String?,
    val numero: String?,
    val complemento: String?,
    val bairro: String?,
    val cidade: String?,
    val estado: String?,
    val latitude: Double?,
    val longitude: Double?,
    val statusAtual: String?,
    val organizadorNome: String?,
    val organizadorEmail: String?,
    val artista: String?,                // nome do artista, null se não houver
    val cacheFinal: Double?,
    val sobreArtista: String?,
    val mediaAvaliacao: Double?,
    val totalAvaliacoes: Int,
    val fotosUrls: List<String>          // URLs das fotos (filtra nulls)
)