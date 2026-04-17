package com.example.sonara.features.recuperarsenha.model

import com.example.sonara.core.ui.state.FieldState

data class RecoverPasswordUiState(
    val email: FieldState = FieldState(),
    val emailAgain: FieldState = FieldState(),
    val isLoading: Boolean = false
)