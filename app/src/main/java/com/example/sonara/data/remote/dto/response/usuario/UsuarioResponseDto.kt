package com.example.sonara.data.remote.dto.response.usuario

import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilArtistaDto
import com.google.gson.annotations.SerializedName

// Cadastro response
data class UsuarioResponseDto(
    @SerializedName("id_usuario")       val idUsuario: Int,
    @SerializedName("nome")             val nome: String,
    @SerializedName("email")            val email: String,
    @SerializedName("cpf")              val cpf: String,
    @SerializedName("data_nasc")        val dataNasc: String?,
    @SerializedName("telefone")         val telefone: String?,
    @SerializedName("foto")             val foto: String? = null,

    @SerializedName("genero")            val genero: UsuarioGeneroDto?,
    @SerializedName("nacionalidade")     val nacionalidade: UsuarioNacionalidadeDto?,
    @SerializedName("endereco")          val endereco: UsuarioEnderecoDto?,
    @SerializedName("redes_sociais")     val redesSociais: List<UsuarioRedesSociaisDto> = emptyList(),
    @SerializedName("artista")           val artista: UsuarioPerfilArtistaDto?


)