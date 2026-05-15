package com.example.sonara.domain.model

data class Usuario(
    val id: Int? = null,
    val nome: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val dataNascimento: String,         // formato yyyy-MM-dd
    val nacionalidadeId: Int? = null,
    val generoId: Int? = null,          // gênero (masculino/feminino etc.)
    val telefone: String? = null,
    val tipoUsuario: String = "usuario", // "artista", "organizador", "usuario"
    val nomeArtistico: String? = null,
    val descricao: String? = null,
    // endereço
    val cep: String? = null,
    val cidade: String? = null,
    val estado: String? = null,
    val logradouro: String? = null,
    val numero: String? = null,
    val complemento: String? = null,
    val bairro: String? = null,
    val longitude: String? = null,
    val latitude: String? = null,
    // ids dos gêneros musicais selecionados
    val generosMusicais: List<Int> = emptyList(),
    // foto
    val fotoPerfil: String? = null
)