package com.example.sonara.features.login.model

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)