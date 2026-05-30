package com.example.sonara.data.remote.dto.response.usuario.perfil

import com.example.sonara.data.remote.dto.response.redesocial.RedeSocialDto
import com.example.sonara.data.remote.dto.response.usuario.UsuarioEnderecoDto
import com.example.sonara.data.remote.dto.response.usuario.UsuarioGeneroDto
import com.example.sonara.data.remote.dto.response.usuario.UsuarioNacionalidadeDto
import com.google.gson.annotations.SerializedName

data class UsuarioPerfilDto(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("nome") val nome: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("cpf") val cpf: String?,
    @SerializedName("data_nasc") val dataNasc: String?,
    @SerializedName("telefone") val telefone: String?,
    @SerializedName("foto") val foto: String?,
    @SerializedName("criado") val criado: String?,
    @SerializedName("ultima_atualizacao") val ultimaAtualizacao: String?,
    @SerializedName("tipo_usuario") val tipoUsuario: String?,
    @SerializedName("genero") val genero: UsuarioGeneroDto?,
    @SerializedName("nacionalidade") val nacionalidade: UsuarioNacionalidadeDto?,
    @SerializedName("endereco") val endereco: UsuarioEnderecoDto?,
    @SerializedName("redes_sociais") val redesSociais: List<RedeSocialDto>?,
    @SerializedName("artista") val artista: UsuarioPerfilArtistaDto?,
    @SerializedName("organizador") val organizador: UsuarioPerfilOrganizadorDto?

)
