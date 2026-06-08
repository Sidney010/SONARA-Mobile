package com.example.sonara.features.organizador.meuseventosorganizador.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.usecase.ListarEventosPorOrganizadorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyEventsOrganizerUiState(
    val eventos: List<Evento> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "Organizador",
    val userRole: String = "ORGANIZADOR",
    val userPhoto: String? = null,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class MyEventsOrganizerViewModel @Inject constructor(
    private val listarEventosPorOrganizadorUseCase: ListarEventosPorOrganizadorUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyEventsOrganizerUiState())
    val uiState: StateFlow<MyEventsOrganizerUiState> = _uiState.asStateFlow()

    init {
        observeSessionAndLoadEvents()
    }

    private fun observeSessionAndLoadEvents() {
        viewModelScope.launch {
            combine(
                tokenManager.userName,
                tokenManager.userType,
                tokenManager.token,
                tokenManager.userPhoto,
                tokenManager.organizerId
            ) { name, type, token, photo, orgId ->
                _uiState.update {
                    it.copy(
                        userName = name ?: "Organizador",
                        userRole = type ?: "ORGANIZADOR",
                        userPhoto = photo,
                        isLoggedIn = !token.isNullOrBlank()
                    )
                }
                orgId?.toIntOrNull()
            }.collect { organizerId ->
                organizerId?.let { loadEventos(it) }
            }
        }
    }

    private fun loadEventos(organizerId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = listarEventosPorOrganizadorUseCase(organizerId)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(eventos = result.data, isLoading = false) }
                }
                is AppResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exception.message ?: "Erro ao carregar eventos"
                        )
                    }
                }
            }
        }
    }
}
