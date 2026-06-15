package com.example.sonara.features.artista.meusEventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.usuarioperfil.UsuarioEventoPerfil
import com.example.sonara.domain.repository.EventoArtistaRepository
import com.example.sonara.domain.usecase.BuscarUsuarioPorIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyEventsUiState(
    val eventos: List<UsuarioEventoPerfil> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "Artista",
    val userRole: String = "ARTISTA",
    val userPhoto: String? = null,
    val userId: Int? = null,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class MyEventsViewModel @Inject constructor(
    private val buscarUsuarioPorIdUseCase: BuscarUsuarioPorIdUseCase,
    private val eventoArtistaRepository: EventoArtistaRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyEventsUiState())
    val uiState: StateFlow<MyEventsUiState> = _uiState.asStateFlow()

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
                tokenManager.userId
            ) { name, type, token, photo, userId ->
                val id = userId?.toIntOrNull()
                _uiState.update {
                    it.copy(
                        userName = name ?: "Artista",
                        userRole = type ?: "ARTISTA",
                        userPhoto = photo,
                        userId = id,
                        isLoggedIn = !token.isNullOrBlank()
                    )
                }
                id
            }.collect { userId ->
                userId?.let { loadEventos(it) }
            }
        }
    }

    fun loadEventos(userId: Int, isRefreshing: Boolean = false) {
        viewModelScope.launch {
            if (isRefreshing) {
                _uiState.update { it.copy(isRefreshing = true, errorMessage = null) }
            } else {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            }
            when (val result = buscarUsuarioPorIdUseCase(userId)) {
                is AppResult.Success -> {
                    val perfil = result.data
                    val nomeArtistico = perfil.artista?.nomeArtistico
                    
                    // Sincroniza o nome artístico com o TokenManager para o header
                    if (perfil.tipoUsuario == "Artista" && !nomeArtistico.isNullOrBlank()) {
                        tokenManager.updateUserName(nomeArtistico)
                    }

                    val candidaturas = perfil.artista?.eventos?.filterNotNull() ?: emptyList()
                    _uiState.update { it.copy(eventos = candidaturas, isLoading = false, isRefreshing = false) }
                }
                is AppResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,

                            isRefreshing = false,
                            errorMessage = result.exception.message ?: "Erro ao carregar seus eventos"
                        )
                    }
                }
            }
        }
    }

    fun cancelCandidacy(idEventoArtista: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = eventoArtistaRepository.deletar(idEventoArtista)) {
                is AppResult.Success -> {
                    val userId = tokenManager.getUserId()
                    if (userId != null) loadEventos(userId)
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Erro ao cancelar candidatura") }
                }
            }
        }
    }
}
