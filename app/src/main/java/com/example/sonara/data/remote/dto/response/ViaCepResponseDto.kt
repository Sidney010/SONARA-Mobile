package com.example.sonara.data.remote.dto.response

// ViaCEP
data class ViaCepResponseDto(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val localidade: String,
    val uf: String,
    val erro: Boolean? = null
)