package com.example.sonara.features.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.core.validation.EmailValidator
import com.example.sonara.core.validation.getErrorOrNull
import com.example.sonara.domain.usecase.BuscarUsuarioPorIdUseCase
import com.example.sonara.domain.usecase.LoginUseCase
import com.example.sonara.features.login.event.LoginEvent
import com.example.sonara.features.login.model.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val buscarUsuarioPorIdUseCase: BuscarUsuarioPorIdUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> get() = _uiState

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    // ── Handlers de campo ─────────────────────────────────────────────

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(
            email = _uiState.value.email.copy(
                value = newEmail,
                error = EmailValidator.validate(newEmail).getErrorOrNull()
            )
        )
    }

    fun onPasswordChange(newPassword: String) {
        val error = if (newPassword.isBlank()) "Senha obrigatória" else null
        _uiState.value = _uiState.value.copy(
            password = _uiState.value.password.copy(value = newPassword, error = error)
        )
    }

    // ── Login ─────────────────────────────────────────────────────────

    fun onLoginClick() {
        val state       = _uiState.value
        val emailError  = EmailValidator.validate(state.email.value).getErrorOrNull()
        val passError   = if (state.password.value.isBlank()) "Senha obrigatória" else null

        _uiState.value = state.copy(
            email    = state.email.copy(error = emailError),
            password = state.password.copy(error = passError)
        )
        if (emailError != null || passError != null) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            when (val result = loginUseCase(state.email.value, state.password.value)) {
                is AppResult.Success -> {
                    val loginData = result.data

                    // Salva sessão com tipo padrão primeiro
                    tokenManager.saveSession(
                        token    = loginData.token,
                        userId   = loginData.usuario.id ?: 0,
                        userName = loginData.usuario.nome,
                        userType = "Usuário"
                    )

                    // Busca perfil completo para obter tipo_usuario real
                    val userId = loginData.usuario.id ?: 0
                    if (userId > 0) {
                        when (val perfil = buscarUsuarioPorIdUseCase(userId)) {
                            is AppResult.Success -> {
                                val tipo = perfil.data.tipoUsuario?.let {
                                    when (it.lowercase()) {
                                        "artista"     -> "Artista"
                                        "organizador" -> "Organizador"
                                        else          -> "Usuário"
                                    }
                                } ?: "Usuário"
                                tokenManager.saveSession(
                                    token    = loginData.token,
                                    userId   = userId,
                                    userName = loginData.usuario.nome,
                                    userType = tipo
                                )
                            }
                            else -> { /* mantém padrão */ }
                        }
                    }

                    _event.emit(LoginEvent.NavigateToHome)
                }
                is AppResult.Error -> {
                    _event.emit(LoginEvent.ShowError(
                        result.exception.message ?: "Erro ao fazer login"
                    ))
                }
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun onForgotClick() {
        viewModelScope.launch { _event.emit(LoginEvent.NavigateToRecoverPassword) }
    }

    fun onSignUpClick() {
        viewModelScope.launch { _event.emit(LoginEvent.NavigateToSignUp) }
    }
}