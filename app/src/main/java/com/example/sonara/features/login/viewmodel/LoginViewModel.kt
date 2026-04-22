package com.example.sonara.features.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.validation.EmailValidator
import com.example.sonara.core.validation.getErrorOrNull
import com.example.sonara.features.login.event.LoginEvent
import com.example.sonara.features.login.model.LoginUiState

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> get() = _uiState

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

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

    fun onPasswordChange(newPassword: String) {
        val current = _uiState.value.password
        _uiState.value = _uiState.value.copy(
            password = current.copy(
                value = newPassword,
            )
        )
    }

    fun onLoginClick() {
        val state = _uiState.value

        val emailError = EmailValidator.validate(state.email.value).getErrorOrNull()
        val passwordError = EmailValidator.validate(state.password.value).getErrorOrNull()

        val updatedState = state.copy(
            email = state.email.copy(error = emailError),
            password = state.password.copy(error = passwordError)
        )

        _uiState.value = updatedState

        if (!isFormValid(updatedState)) return


    }
    private fun isFormValid(state: LoginUiState): Boolean {
        return state.email.error == null &&
                state.password.error == null
    }
    fun onForgotClick() {
        viewModelScope.launch {
            _event.emit(LoginEvent.NavigateToRecoverPassword)
        }
    }
    fun onSignUpClick() {
        viewModelScope.launch {
            _event.emit(LoginEvent.NavigateToSignUp)
        }
    }
}