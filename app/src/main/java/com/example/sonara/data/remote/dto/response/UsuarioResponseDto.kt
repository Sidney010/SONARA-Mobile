package com.example.sonara.data.remote.dto.response

import com.example.sonara.data.remote.dto.response.usuario.UsuarioArtistaDto
import com.example.sonara.data.remote.dto.response.usuario.UsuarioEnderecoDto
import com.example.sonara.data.remote.dto.response.usuario.UsuarioGeneroDto
import com.example.sonara.data.remote.dto.response.usuario.UsuarioNacionalidadeDto
import com.example.sonara.data.remote.dto.response.usuario.UsuarioRedesSociaisDto
import com.google.gson.annotations.SerializedName

// Cadastro response
data class UsuarioResponseDto(
    @SerializedName("id_usuario") val id_usuario: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("cpf") val cpf: String,
    @SerializedName("data_nasc") val data_nasc: String?,
    @SerializedName("telefone") val telefone: String?,
    @SerializedName("foto") val foto: String? = null,

    @SerializedName("genero")            val genero: UsuarioGeneroDto?,
    @SerializedName("nacionalidade")     val nacionalidade: UsuarioNacionalidadeDto?,
    @SerializedName("endereco")          val endereco: UsuarioEnderecoDto?,
    @SerializedName("redes_sociais")     val redes_sociais: List<UsuarioRedesSociaisDto> = emptyList(),
    @SerializedName("artista")           val artista: UsuarioArtistaDto?,


    )


