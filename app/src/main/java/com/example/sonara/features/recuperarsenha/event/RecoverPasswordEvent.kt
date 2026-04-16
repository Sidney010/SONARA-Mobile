package com.example.sonara.features.recuperarsenha.event

sealed class RecoverPasswordEvent {
    object NavigateToRedefinedPassword : RecoverPasswordEvent()
    data class ShowError(val message: String) : RecoverPasswordEvent()
}