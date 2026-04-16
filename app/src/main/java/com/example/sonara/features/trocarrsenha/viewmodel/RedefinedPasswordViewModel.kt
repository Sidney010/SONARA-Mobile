package com.example.sonara.features.trocarrsenha.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sonara.features.trocarrsenha.model.RedefinedPasswordUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.sonara.features.trocarrsenha.event.RedefinedPasswordEvent

class RedefinedPasswordViewModel : ViewModel() {
    private val _uiState = mutableStateOf(RedefinedPasswordUiState())
    val uiState: State<RedefinedPasswordUiState> get() = _uiState
    private val _event = MutableSharedFlow<RedefinedPasswordEvent>()
    val event = _event.asSharedFlow()

    fun onPasswordChange(newPassword: String) {
        if (newPassword.length <= 50) {
            val error = getPasswordError(newPassword)
            _uiState.value = _uiState.value.copy(
                password = newPassword,
                isPasswordValid = error == null,
                errorMessage = error
            )
        }
    }

    fun onPasswordAgainChange(newPasswordAgain: String) {
        if (newPasswordAgain.length <= 50) {
            val error = getPasswordError(newPasswordAgain)
            _uiState.value = _uiState.value.copy(
                passwordAgain = newPasswordAgain,
                isPasswordAgainValid = error == null
            )
        }
    }

    private fun emitError(message: String) {
        viewModelScope.launch {
            _event.emit(RedefinedPasswordEvent.ShowError(message))
        }
    }

    fun onRedefinedPasswordClick() {
        val state = _uiState.value

        val passwordError = getPasswordError(state.password)
        val passwordAgainError = getPasswordError(state.passwordAgain)

        if (passwordError != null) {
            emitError(passwordError)
            return
        }

        if (passwordAgainError != null) {
            emitError(passwordAgainError)
            return
        }

        if (state.password != state.passwordAgain) {
            emitError("As senhas não coincidem")
            return
        }

        viewModelScope.launch {
            _event.emit(RedefinedPasswordEvent.NavigateToLogin)
        }
    }

    private fun validatePassword(password: String): Boolean {
        val hasMinLength = password.length >= 8
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return hasMinLength &&
                hasUpperCase &&
                hasLowerCase &&
                hasDigit &&
                hasSpecialChar
    }
    private fun getPasswordError(password: String): String? {
        if (password.length < 8) return "Mínimo 8 caracteres"
        if (!password.any { it.isUpperCase() }) return "Precisa de letra maiúscula"
        if (!password.any { it.isLowerCase() }) return "Precisa de letra minúscula"
        if (!password.any { it.isDigit() }) return "Precisa de número"
        if (!password.any { !it.isLetterOrDigit() }) return "Precisa de caractere especial"

        return null
    }
}