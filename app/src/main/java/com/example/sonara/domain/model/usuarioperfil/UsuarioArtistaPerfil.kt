package com.example.sonara.domain.model.usuarioperfil

import com.example.sonara.domain.model.GeneroMusical


data class UsuarioArtistaPerfil (
    val idAtista: Int?,
    val nomeArtistico: String?,
    val descricao: String?,
    val generosMusicais: List<GeneroMusical>,
    val mediaAvaliacao: Double?,
    val totalAvaliacoes: Int?,
    val eventos: List<UsuarioEventoPerfil?> = emptyList(),
)




