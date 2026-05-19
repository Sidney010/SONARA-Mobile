package com.example.sonara.core.ui.components.header

data class HeaderUiState(
    val userName: String,
    val userRole: String,
    val avatarUrl: String? = null,
    val isLoggedIn: Boolean = false   // NOVO: controla o comportamento do avatar
)