package com.example.sonara.features.cadastrar.model

data class AddressUiState(

    val cep: String = "",
    val cepError: String? = null,

    val rua: String = "",
    val ruaError: String? = null,

    val bairro: String = "",
    val bairroError: String? = null,

    val cidade: String = "",
    val cidadeError: String? = null,

    val uf: String = "",
    val ufError: String? = null,

    val isLoading: Boolean = false
)