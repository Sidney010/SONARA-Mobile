package com.example.sonara.domain.model.usuarioperfil

import com.example.sonara.domain.model.Fotos

data class UsuarioEventoPerfil(
    val idEvento: Int,
    val eventoData: String?,
    val eventoNome: String?,
    val cache: UsuarioCachePerfil?,
    val status: String?,
    val fotos: List<Fotos?>? = emptyList(),
    val endereco: UsuarioPerfilEventosEndereco?,
    val horaFim: String?,
    val descricao: String?,
    val horaInicio: String?,
    val sobreArtista: String?,
    val motivoInscricao: String?,
    val idEventoArtista: Int? = null

)