package com.example.sonara.features.cadastrar.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sonara.core.validation.EmailValidator
import com.example.sonara.core.validation.NomeValidator
import com.example.sonara.core.validation.PasswordValidator
import com.example.sonara.core.validation.getErrorOrNull
import com.example.sonara.features.cadastrar.model.SignUpUIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SignUpViewModel: ViewModel() {
    private val _uiState = mutableStateOf(SignUpUIState())

    val uiState: State<SignUpUIState> get() = _uiState

    private val _event = MutableSharedFlow<SignUpUIState>()
    val event = _event.asSharedFlow()

    fun onNomeChange(newNome: String) {
        val current = _uiState.value.email
        val error = NomeValidator.validate(newNome).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            email = current.copy(
                value = newNome,
                error = error
            )
        )
    }

    fun onEmailChange(newEmail: String) {
        val current = _uiState.value.email
        val error = EmailValidator.validate(newEmail).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            email = current.copy(
                value = newEmail,
                error = error
            )
        )
    }

    fun onEmailAgainChange(newEmailAgain: String) {
        val current = _uiState.value.emailAgain
        val error = EmailValidator.validate(newEmailAgain).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            emailAgain = current.copy(
                value = newEmailAgain,
                error = error
            )
        )
    }

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
}