package com.example.sonara.features.artista.sobreEvento.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.usecase.BuscarUsuarioPorIdUseCase
import com.example.sonara.domain.usecase.GetEventoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AboutEventUiState(
    val evento: Evento? = null,
    val eventoArtistaId: Int? = null,
    val statusCandidatura: String? = null,
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
    private val buscarUsuarioPorIdUseCase: BuscarUsuarioPorIdUseCase,
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
            
            val eventResult = getEventoByIdUseCase(id)
            
            if (eventResult is AppResult.Success) {
                val userId = tokenManager.userId.first()?.toIntOrNull()
                var existingEaId: Int? = null
                var status: String? = null
                
                if (userId != null) {
                    when (val userResult = buscarUsuarioPorIdUseCase(userId)) {
                        is AppResult.Success -> {
                            val eventArtist = userResult.data.artista?.eventos?.find { it?.idEvento == id }
                            existingEaId = eventArtist?.idEventoArtista
                            status = eventArtist?.status
                        }
                        else -> { /* ignore profile load error */ }
                    }
                }

                _uiState.update { 
                    it.copy(
                        evento = eventResult.data, 
                        eventoArtistaId = existingEaId,
                        statusCandidatura = status,
                        isLoading = false 
                    ) 
                }
            } else if (eventResult is AppResult.Error) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = eventResult.exception.message ?: "Erro ao carregar detalhes do evento"
                    )
                }
            }
        }
    }
}
