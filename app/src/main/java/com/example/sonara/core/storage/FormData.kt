package com.example.sonara.core.storage

data class FormData(
    // Dados pessoais básicos
    val name: String         = "",
    val email: String        = "",
    val cpf: String          = "",
    val password: String     = "",
    val image: String?       = null,
    val dataNasc: String     = "",          // "yyyy-MM-dd"
    val telefone: String     = "",

    // Tipo de usuário (único valor)
    val userType: String     = "",          // "artista" | "organizador" | "user"

    // Dados artísticos (opcionais)
    val nomeArtistico: String = "",
    val descricao: String     = "",

    // Catálogos (armazenados como ID)
    val nacionalidadeId: String = "",       // Int serializado
    val generoId: String        = "",       // Int serializado (gênero da pessoa)
    val generosMusicais: String = "",       // IDs separados por vírgula

    // Endereço
    val cep: String          = "",
    val rua: String          = "",
    val bairro: String       = "",
    val cidade: String       = "",
    val uf: String           = "",
    val numero: String       = "",
    val complemento: String  = "",

    // Redes sociais — formato: "tipoId:link|tipoId:link|..."
    // Exemplo: "1:instagram.com/user|3:youtube.com/channel"
    // String vazia = nenhuma rede social cadastrada
    val redesSociais: String = ""
)