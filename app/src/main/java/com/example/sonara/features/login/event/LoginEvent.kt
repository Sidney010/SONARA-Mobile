package com.example.sonara.features.login.event

sealed class LoginEvent {
    object NavigateToRecoverPassword : LoginEvent()
    object NavigateToSignUp          : LoginEvent()
    object NavigateToHome            : LoginEvent()     // NOVO: após login bem-sucedido
    data class ShowError(val message: String) : LoginEvent()
}