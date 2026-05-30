package com.example.sonara.domain.model.usuarioperfil

data class UsuarioPerfilEventosEndereco(
    val cep: String?,
    val bairro: String?,
    val cidade: String?,
    val estado: String?,
    val numero: String?,
    val latitude: String?,
    val longitude: String?,
    val logradouro: String?,
    val complemento: String?,
    val idEnderecoEvento: Int?
)