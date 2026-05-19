package com.example.sonara.features.perfilartista.model

import com.example.sonara.domain.model.UsuarioPerfil

data class ArtistProfileUiState(
    val perfil: UsuarioPerfil?    = null,
    val isLoading: Boolean        = false,
    val isSaving: Boolean         = false,
    val errorMessage: String?     = null,
    val successMessage: String?   = null,
    val isEditing: Boolean        = false     // futuro: habilita campos para edição
)