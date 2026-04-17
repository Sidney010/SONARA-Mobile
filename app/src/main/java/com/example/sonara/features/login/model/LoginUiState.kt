package com.example.sonara.features.login.model

import com.example.sonara.core.ui.state.FieldState

data class LoginUiState(
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val isLoading: Boolean = false,
)