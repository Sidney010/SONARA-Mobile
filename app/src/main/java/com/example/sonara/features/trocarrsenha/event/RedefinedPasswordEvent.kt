package com.example.sonara.features.trocarrsenha.event

sealed class RedefinedPasswordEvent {
    object NavigateToLogin : RedefinedPasswordEvent()
    data class ShowError(val message: String) : RedefinedPasswordEvent()
}