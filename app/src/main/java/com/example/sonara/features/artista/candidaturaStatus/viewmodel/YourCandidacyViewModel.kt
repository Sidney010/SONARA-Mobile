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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class YourCandidacyUiState(
    val isLoading: Boolean = false,
    val evento: Evento? = null,
    val eventoArtista: EventoArtista? = null,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val userName: String = "",
    val userRole: String = ""
)

@HiltViewModel
class YourCandidacyViewModel @Inject constructor(
    private val eventoRepository: EventoRepository,
    private val eventoArtistaRepository: EventoArtistaRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(YourCandidacyUiState())
    val uiState: StateFlow<YourCandidacyUiState> = _uiState.asStateFlow()

    fun loadData(eventoId: Int, eventoArtistaId: Int? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val name = tokenManager.userName.first() ?: ""
            val role = tokenManager.userType.first() ?: ""
            _uiState.value = _uiState.value.copy(userName = name, userRole = role)

            val eventoResult = eventoRepository.buscarEventoPorId(eventoId)
            if (eventoResult is AppResult.Success) {
                _uiState.value = _uiState.value.copy(evento = eventoResult.data)
            }

            if (eventoArtistaId != null) {
                val eaResult = eventoArtistaRepository.buscarPorId(eventoArtistaId)
                if (eaResult is AppResult.Success) {
                    _uiState.value = _uiState.value.copy(eventoArtista = eaResult.data)
                }
            }
            
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun submitCandidacy(
        eventoId: Int,
        cacheEsperado: Double,
        sobreArtista: String,
        motivoInscricao: String? = null
    ) {
        viewModelScope.launch {
            val artistaId = tokenManager.artistId.first()?.toIntOrNull() ?: return@launch
            
            val currentEA = _uiState.value.eventoArtista
            val result = if (currentEA != null) {
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
                        cacheOfertado = 0.0,
                        cacheFinal = 0.0,
                        contraProposta = 0.0,
                        sobreArtista = sobreArtista,
                        motivoInscricao = motivoInscricao
                    )
                )
            }

            when (result) {
                is AppResult.Success -> {
                    _uiState.value = _uiState.value.copy(isSuccess = true, eventoArtista = result.data)
                }
                is AppResult.Error -> {
                    _uiState.value = _uiState.value.copy(error = "Erro ao salvar candidatura")
                }
            }
        }
    }

    fun deleteCandidacy() {
        val eaId = _uiState.value.eventoArtista?.idEventoArtista ?: return
        viewModelScope.launch {
            when (val result = eventoArtistaRepository.deletar(eaId)) {
                is AppResult.Success -> {
                    _uiState.value = _uiState.value.copy(isSuccess = true, eventoArtista = null)
                }
                is AppResult.Error -> {
                    _uiState.value = _uiState.value.copy(error = "Erro ao deletar candidatura")
                }
            }
        }
    }
}