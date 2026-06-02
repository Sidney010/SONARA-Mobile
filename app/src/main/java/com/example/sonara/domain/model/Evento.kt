package com.example.sonara.domain.model

data class Evento(
    val id: Int,
    val nome: String,
    val descricao: String?,
    val local: String?,
    val data: String?,
    val horaInicio: String?,
    val horaFim: String?,
    val fotosUrls: List<String>,
    val mediaAvaliacao: Double?,
    val totalAvaliacoes: Int,

    // Endereço
    val logradouro: String?,
    val numero: String?,
    val bairro: String?,
    val cidade: String?,
    val estado: String?,
    val cep: String?,
    val complemento: String?,
    val latitude: String?,
    val longitude: String?,

    // Organizador
    val organizadorNome: String?,
    val organizadorEmail: String?,
    // Artista principal (para exibição rápida)
    val artista: ArtistaResumo? = null
)

data class ArtistaResumo(
    val id: Int?,
    val nome: String?,
    val foto: String?,
    val sobre: String?
)