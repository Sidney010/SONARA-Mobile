package com.example.sonara.features.trocarrsenha.model

import kotlin.String

data class RedefinedPasswordUiState(
    val password: String = "",
    val passwordAgain: String = "",
    val isPasswordValid: Boolean = true,
    val isPasswordAgainValid: Boolean = true,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)