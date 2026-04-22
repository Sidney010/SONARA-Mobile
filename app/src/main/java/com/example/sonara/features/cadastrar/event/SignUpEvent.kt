package com.example.sonara.features.cadastrar.event

sealed class SignUpEvent {

    object NavigateToLogin : SignUpEvent()

    data class ShowError(val message: String) : SignUpEvent()
}