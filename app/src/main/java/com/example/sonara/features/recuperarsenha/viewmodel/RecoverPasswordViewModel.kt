package com.example.sonara.features.recuperarsenha.viewmodel

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.features.recuperarsenha.event.RecoverPasswordEvent
import com.example.sonara.features.recuperarsenha.model.RecoverPasswordUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RecoverPasswordViewModel : ViewModel() {

    private val _uiState = mutableStateOf(RecoverPasswordUiState())
    val uiState: State<RecoverPasswordUiState> get() = _uiState
    private val _event = MutableSharedFlow<RecoverPasswordEvent>()
    val event = _event.asSharedFlow()

    fun onEmailChange(newEmail: String) {
        if (newEmail.length <= 50) {
            _uiState.value = _uiState.value.copy(
                email = newEmail,
                isEmailValid = validateEmail(newEmail),
            )
        }
    }
    fun onEmailAgainChange(newEmailAgain: String) {
        if (newEmailAgain.length <= 50) {
            _uiState.value = _uiState.value.copy(
                emailAgain = newEmailAgain,
                isEmailAgainValid = validateEmail(newEmailAgain),
            )
        }
    }

    fun onRecoverPasswordClick() {
        val state = _uiState.value

        if (!validateEmail(state.email)) {
            emitError("Email inválido")
            return
        }

        if (!validateEmail(state.emailAgain)) {
            emitError("Confirmação inválida")
            return
        }

        if (state.email != state.emailAgain) {
            emitError("Emails não coincidem")
            return
        }

        viewModelScope.launch {
            _event.emit(RecoverPasswordEvent.NavigateToRedefinedPassword)
        }
    }

    private fun emitError(message: String) {
        viewModelScope.launch {
            _event.emit(RecoverPasswordEvent.ShowError(message))
        }
    }

    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}