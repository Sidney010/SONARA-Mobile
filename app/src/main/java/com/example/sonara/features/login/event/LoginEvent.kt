package com.example.sonara.features.login.event


sealed class LoginEvent {
    object NavigateToRecoverPassword : LoginEvent()
    data class ShowError(val message: String) : LoginEvent()
}