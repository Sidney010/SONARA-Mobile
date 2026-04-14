package com.example.sonara.features.login.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sonara.features.login.model.LoginUiState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class LoginViewModel : ViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> get() = _uiState

    fun onEmailChange(newEmail: String) {
        if (newEmail.length <= 50) {
            _uiState.value = _uiState.value.copy(
                email = newEmail,
                isEmailValid = validateEmail(newEmail)
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        if (newPassword.length <= 50) {
            _uiState.value = _uiState.value.copy(
                password = newPassword,
                isPasswordValid = validatePassword(newPassword)
            )
        }
    }

    fun onLoginClick() {
        val state = _uiState.value

        if (!state.isEmailValid || !state.isPasswordValid) {
            _uiState.value = state.copy(
                errorMessage = "Dados inválidos"
            )
            return
        }

        _uiState.value = state.copy(isLoading = true)

    }

    private fun validateEmail(email: String): Boolean {
        return email.contains("@") && email.length >= 5
    }

    private fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }
}