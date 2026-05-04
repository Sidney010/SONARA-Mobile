package com.example.sonara.features.trocarrsenha.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.validation.PasswordValidator
import com.example.sonara.core.validation.getErrorOrNull
import com.example.sonara.features.trocarrsenha.event.RedefinedPasswordEvent
import com.example.sonara.features.trocarrsenha.model.RedefinedPasswordUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RedefinedPasswordViewModel : ViewModel() {

    private val _uiState = mutableStateOf(RedefinedPasswordUiState())
    val uiState: State<RedefinedPasswordUiState> get() = _uiState

    private val _event = MutableSharedFlow<RedefinedPasswordEvent>()
    val event = _event.asSharedFlow()

    fun onPasswordChange(newPassword: String) {
        val error = PasswordValidator.validate(newPassword).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            password = _uiState.value.password.copy(
                value = newPassword,
                error = error,
                isTouched = true
            )
        )
    }

    fun onPasswordAgainChange(newPasswordAgain: String) {
        val error = PasswordValidator.validate(newPasswordAgain).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            passwordAgain = _uiState.value.passwordAgain.copy(
                value = newPasswordAgain,
                error = error,
                isTouched = true
            )
        )
    }

    fun onRedefinedPasswordClick() {
        val state = _uiState.value

        val passwordError = PasswordValidator.validate(state.password.value).getErrorOrNull()
        val passwordAgainError = PasswordValidator.validate(state.passwordAgain.value).getErrorOrNull()

        val updatedState = state.copy(
            password = state.password.copy(error = passwordError, isTouched = true),
            passwordAgain = state.passwordAgain.copy(error = passwordAgainError, isTouched = true)
        )

        _uiState.value = updatedState

        // validação geral
        if (!isFormValid(updatedState)) return

        // regra de negócio
        if (state.password.value != state.passwordAgain.value) {
            _uiState.value = updatedState.copy(
                passwordAgain = updatedState.passwordAgain.copy(
                    error = "As senhas não coincidem"
                )
            )
            return
        }

        // sucesso
        viewModelScope.launch {
            _event.emit(RedefinedPasswordEvent.NavigateToLogin)
        }
    }

    private fun isFormValid(state: RedefinedPasswordUiState): Boolean {
        return state.password.error == null &&
                state.passwordAgain.error == null
    }
}