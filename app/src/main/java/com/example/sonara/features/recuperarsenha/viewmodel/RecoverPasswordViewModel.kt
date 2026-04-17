package com.example.sonara.features.recuperarsenha.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.validation.EmailValidator
import com.example.sonara.core.validation.getErrorOrNull
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

    fun onRecoverPasswordClick() {
        val state = _uiState.value

        val emailError = EmailValidator.validate(state.email.value).getErrorOrNull()
        val emailAgainError = EmailValidator.validate(state.emailAgain.value).getErrorOrNull()

        val updatedState = state.copy(
            email = state.email.copy(error = emailError),
            emailAgain = state.emailAgain.copy(error = emailAgainError)
        )

        _uiState.value = updatedState

        if (!isFormValid(updatedState)) return

        viewModelScope.launch {
            _event.emit(RecoverPasswordEvent.NavigateToRedefinedPassword)
        }
    }

    private fun isFormValid(state: RecoverPasswordUiState): Boolean {
        return state.email.error == null &&
                state.emailAgain.error == null &&
                state.email.value == state.emailAgain.value
    }

}