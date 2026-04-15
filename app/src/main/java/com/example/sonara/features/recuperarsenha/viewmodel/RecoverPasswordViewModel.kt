package com.example.sonara.features.recuperarsenha.viewmodel

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sonara.features.recuperarsenha.model.RecoverPasswordUiState

class RecoverPasswordViewModel : ViewModel() {

    private val _uiState = mutableStateOf(RecoverPasswordUiState())
    val uiState: State<RecoverPasswordUiState> get() = _uiState

    fun onEmailChange(newEmail: String) {
        if (newEmail.length <= 50) {
            _uiState.value = _uiState.value.copy(
                email = newEmail,
                isEmailValid = validateEmail(newEmail),
                errorMessage = null // limpa erro ao digitar
            )
        }
    }

    fun onEmailAgainChange(newEmailAgain: String) {
        if (newEmailAgain.length <= 50) {
            _uiState.value = _uiState.value.copy(
                emailAgain = newEmailAgain,
                isEmailAgainValid = validateEmail(newEmailAgain),
                errorMessage = null // limpa erro ao digitar
            )
        }
    }

    fun onRecoverPasswordClick() {
        val state = _uiState.value

        if (!state.isEmailValid) {
            _uiState.value = state.copy(
                errorMessage = "Email inválido"
            )
            return
        }

        if (!state.isEmailAgainValid) {
            _uiState.value = state.copy(
                errorMessage = "Confirmação de email inválida"
            )
            return
        }

        if (state.email != state.emailAgain) {
            _uiState.value = state.copy(
                errorMessage = "Os emails não coincidem"
            )
            return
        }

        _uiState.value = state.copy(
            isLoading = true,
            errorMessage = null
        )

        // API / Firebase / backend
    }

    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}