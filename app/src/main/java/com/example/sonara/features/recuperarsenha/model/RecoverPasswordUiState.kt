package com.example.sonara.features.recuperarsenha.model

data class RecoverPasswordUiState (
    val email: String = "",
    val emailAgain: String = "",
    val isEmailValid: Boolean = true,
    val isEmailAgainValid: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)