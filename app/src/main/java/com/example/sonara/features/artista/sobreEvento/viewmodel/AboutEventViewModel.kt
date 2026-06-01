package com.example.sonara.features.artista.sobreEvento.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.usecase.GetEventoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AboutEventUiState(
    val evento: Evento? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "Anônimo",
    val userRole: String = "Usuário",
    val userPhoto: String? = null,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class AboutEventViewModel @Inject constructor(
    private val getEventoByIdUseCase: GetEventoByIdUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AboutEventUiState())
    val uiState: StateFlow<AboutEventUiState> = _uiState.asStateFlow()

    init {
        observeSession()
    }

    private fun observeSession() {
        viewModelScope.launch {
            combine(
                tokenManager.userName,
                tokenManager.userType,
                tokenManager.token,
                tokenManager.userPhoto
            ) { name, type, token, photo ->
                listOf(name, type, token, photo)
            }.collect { (name, type, token, photo) ->
                _uiState.update {
                    it.copy(
                        userName = (name as? String) ?: "Anônimo",
                        userRole = (type as? String) ?: "Usuário",
                        userPhoto = photo as? String,
                        isLoggedIn = !(token as? String).isNullOrBlank()
                    )
                }
            }
        }
    }

    fun loadEvento(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getEventoByIdUseCase(id)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(evento = result.data, isLoading = false) }
                }
                is AppResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exception.message ?: "Erro ao carregar detalhes do evento"
                        )
                    }
                }
            }
        }
    }
}
