package com.example.sonara.features.plano.model

data class PlansUiState(
    val userName:  String  = "Anônimo",
    val userRole:  String  = "Usuário",
    val userPhoto: String? = null,
    val isLoggedIn: Boolean = false
)