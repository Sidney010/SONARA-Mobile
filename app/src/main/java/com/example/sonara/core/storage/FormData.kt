package com.example.sonara.core.storage

data class FormData(

    val name: String = "",
    val email: String = "",
    val cpf: String = "",
    val password: String = "",
    val image: String? = null,
    val userType: String = "",

    // ENDEREÇO
    val cep: String = "",
    val rua: String = "",
    val bairro: String = "",
    val cidade: String = "",
    val uf: String = ""
)