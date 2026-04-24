package com.example.sonara.features.trocarrsenha.model

import com.example.sonara.core.form.FieldState


data class RedefinedPasswordUiState(
    val password: FieldState = FieldState(),
    val passwordAgain: FieldState = FieldState(),
    val isLoading: Boolean = false
)