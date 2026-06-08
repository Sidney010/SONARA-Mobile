package com.example.sonara.features.artista.perfilartista.model

import com.example.sonara.domain.model.TipoRedeSocial
import com.example.sonara.domain.model.usuarioperfil.UsuarioPerfil
import com.example.sonara.features.cadastrar.model.RedeSocialDraft

data class ArtistProfileUiState(
    val perfil: UsuarioPerfil?    = null,
    val isLoading: Boolean        = false,
    val isSaving: Boolean         = false,
    val errorMessage: String?     = null,
    val successMessage: String?   = null,
    val isEditing: Boolean        = false,     // futuro: habilita campos para edição
    val redesSociais: List<RedeSocialDraft> = emptyList(),
    val tiposRedesSociais: List<TipoRedeSocial> = emptyList()
)