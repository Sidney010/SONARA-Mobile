package com.example.sonara.core.ui.components.header

data class HeaderUiState(
    val userName: String = "Anonimo",
    val userRole: String = "Usuario",
    val avatarUrl: String? = null,
    val isLoggedIn: Boolean = false   // NOVO: controla o comportamento do avatar
)