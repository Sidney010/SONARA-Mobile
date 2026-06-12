package com.example.sonara.features.organizador.meuseventosorganizador.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.usecase.GetUsuarioPerfilUseCase
import com.example.sonara.data.mapper.toDomain
import com.example.sonara.data.mapper.toEventoDomain
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
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "Organizador",
    val userRole: String = "ORGANIZADOR",
    val userPhoto: String? = null,
    val userId: Int? = null,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class MyEventsOrganizerViewModel @Inject constructor(
    private val getUsuarioPerfilUseCase: GetUsuarioPerfilUseCase,
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
                tokenManager.userId,
                tokenManager.token
            ) { userId, token ->
                val id = userId?.toIntOrNull()
                _uiState.update {
                    it.copy(
                        isLoggedIn = !token.isNullOrBlank(),
                        userId = id
                    )
                }
                id
            }.collect { userId ->
                userId?.let { loadEventosDoPerfil(it) }
            }
        }
    }

    fun loadEventosDoPerfil(userId: Int, isRefreshing: Boolean = false) {
        viewModelScope.launch {
            if (isRefreshing) {
                _uiState.update { it.copy(isRefreshing = true, errorMessage = null) }
            } else {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            }
            when (val result = getUsuarioPerfilUseCase(userId)) {
                is AppResult.Success -> {
                    val perfil = result.data
                    val eventosDomain = perfil.organizador?.eventos?.map { it.toEventoDomain() } ?: emptyList()
                    
                    _uiState.update { it.copy(
                        eventos = eventosDomain,
                        isLoading = false,
                        isRefreshing = false,
                        userName = perfil.nome ?: it.userName,
                        userPhoto = perfil.foto ?: it.userPhoto,
                        userRole = perfil.tipoUsuario ?: it.userRole
                    ) }
                }
                is AppResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            errorMessage = result.exception.message ?: "Erro ao carregar eventos"
                        )
                    }
                }
            }
        }
    }
}
