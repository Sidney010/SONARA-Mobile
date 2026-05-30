package com.example.sonara.domain.model.usuarioperfil

import com.example.sonara.data.remote.dto.response.usuario.perfil.UsuarioPerfilOrganizadorDto
import com.example.sonara.domain.model.Endereco
import com.example.sonara.domain.model.Genero
import com.example.sonara.domain.model.Nacionalidade
import com.example.sonara.domain.model.RedeSocial

data class UsuarioPerfil(
    val idUsuario: Int,
    val nome: String,
    val email: String,
    val cpf: String?,
    val dataNasc: String?,
    val telefone: String?,
    val foto: String?,
    val criado: String?,
    val ultimaAtualizacao: String?,
    val tipoUsuario: String,
    val genero: Genero?,
    val nacionalidade: Nacionalidade?,
    val endereco: Endereco?,
    val redesSociais: List<RedeSocial>,
    val artista: UsuarioArtistaPerfil?,
    val organizador: UsuarioPerfilOrganizadorDto?
)