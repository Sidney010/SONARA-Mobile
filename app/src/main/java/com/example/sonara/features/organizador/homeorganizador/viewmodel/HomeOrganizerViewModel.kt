package com.example.sonara.features.organizador.homeorganizador.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeOrganizerUiState(
    val userName: String = "Organizador",
    val userRole: String = "ORGANIZADOR",
    val userPhoto: String? = null,
    val isLoading: Boolean = false,
    val events: List<com.example.sonara.domain.model.Evento> = emptyList()
)

@HiltViewModel
class HomeOrganizerViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val listarEventosUseCase: com.example.sonara.domain.usecase.ListarEventosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeOrganizerUiState())
    val uiState: StateFlow<HomeOrganizerUiState> = _uiState.asStateFlow()

    init {
        observeSession()
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = listarEventosUseCase()) {
                is com.example.sonara.core.common.AppResult.Success -> {
                    _uiState.update { it.copy(events = result.data, isLoading = false) }
                }
                is com.example.sonara.core.common.AppResult.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun observeSession() {
        viewModelScope.launch {
            combine(
                tokenManager.userName,
                tokenManager.userType,
                tokenManager.userPhoto
            ) { name, type, photo ->
                _uiState.update {
                    it.copy(
                        userName = name ?: "Organizador",
                        userRole = type ?: "ORGANIZADOR",
                        userPhoto = photo
                    )
                }
            }.collect {}
        }
    }
}
