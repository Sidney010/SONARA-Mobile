package com.example.sonara.features.artista.candidaturaStatus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.model.EventoArtista
import com.example.sonara.domain.repository.EventoArtistaRepository
import com.example.sonara.domain.repository.EventoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class YourCandidacyUiState(
    val isLoading: Boolean = false,
    val evento: Evento? = null,
    val eventoArtista: EventoArtista? = null,
    val error: String? = null,
    val successMessage: String? = null,
    val isSuccess: Boolean = false,
    val userName: String = "Anônimo",
    val userRole: String = "Artista",
    val userPhoto: String? = null,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class YourCandidacyViewModel @Inject constructor(
    private val eventoRepository: EventoRepository,
    private val eventoArtistaRepository: EventoArtistaRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(YourCandidacyUiState())
    val uiState: StateFlow<YourCandidacyUiState> = _uiState.asStateFlow()

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
                _uiState.update {
                    it.copy(
                        userName = name ?: "Anônimo",
                        userRole = type ?: "Artista",
                        userPhoto = photo,
                        isLoggedIn = !token.isNullOrBlank()
                    )
                }
            }.collect {}
        }
    }

    fun loadData(eventoId: Int, eventoArtistaId: Int? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, isSuccess = false, successMessage = null) }

            val eventoResult = eventoRepository.buscarEventoPorId(eventoId)
            if (eventoResult is AppResult.Success) {
                _uiState.update { it.copy(evento = eventoResult.data) }
            }

            if (eventoArtistaId != null && eventoArtistaId != 0) {
                val eaResult = eventoArtistaRepository.buscarPorId(eventoArtistaId)
                if (eaResult is AppResult.Success) {
                    _uiState.update { it.copy(eventoArtista = eaResult.data) }
                }
            }
            
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun submitCandidacy(
        eventoId: Int,
        cacheEsperado: Double,
        sobreArtista: String,
        motivoInscricao: String
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val artistaIdStr = tokenManager.artistId.first()
            val artistaId = artistaIdStr?.toIntOrNull()
            
            if (artistaId == null) {
                _uiState.update { it.copy(isLoading = false, error = "ID do artista não encontrado. Faça login novamente.") }
                return@launch
            }
            
            val currentEA = _uiState.value.eventoArtista
            val result = if (currentEA != null && currentEA.idEventoArtista != 0) {
                // Atualizar
                eventoArtistaRepository.atualizar(
                    currentEA.idEventoArtista,
                    currentEA.copy(
                        cacheEsperado = cacheEsperado,
                        sobreArtista = sobreArtista,
                        motivoInscricao = motivoInscricao
                    )
                )
            } else {
                // Criar
                eventoArtistaRepository.criar(
                    EventoArtista(
                        idEventoArtista = 0,
                        artistaId = artistaId,
                        eventoId = eventoId,
                        cacheEsperado = cacheEsperado,
                        cacheOfertado = null,
                        cacheFinal = null,
                        contraProposta = null,
                        sobreArtista = sobreArtista,
                        motivoInscricao = motivoInscricao
                    )
                )
            }

            when (result) {
                is AppResult.Success -> {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            isSuccess = true, 
                            eventoArtista = result.data,
                            successMessage = if (currentEA != null && currentEA.idEventoArtista != 0) "Candidatura atualizada com sucesso!" else "Candidatura enviada com sucesso!"
                        ) 
                    }
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = "Erro ao salvar candidatura: ${result.exception.message}") }
                }
            }
        }
    }

    fun deleteCandidacy() {
        val eaId = _uiState.value.eventoArtista?.idEventoArtista ?: return
        if (eaId == 0) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = eventoArtistaRepository.deletar(eaId)) {
                is AppResult.Success -> {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            isSuccess = true, 
                            eventoArtista = null,
                            successMessage = "Candidatura cancelada com sucesso!"
                        ) 
                    }
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = "Erro ao deletar candidatura") }
                }
            }
        }
    }
}
