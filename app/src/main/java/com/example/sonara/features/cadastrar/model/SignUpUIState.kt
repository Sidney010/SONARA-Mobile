package com.example.sonara.features.cadastrar.model

import com.example.sonara.core.ui.state.FieldState

data class SignUpUIState (
    val nome: FieldState = FieldState(),
    val cpf: FieldState = FieldState(),
    val dataNascimento: FieldState = FieldState(),
    val email: FieldState = FieldState(),
    val emailAgain: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val passwordAgain: FieldState = FieldState(),
    val isLoading: Boolean = false
)