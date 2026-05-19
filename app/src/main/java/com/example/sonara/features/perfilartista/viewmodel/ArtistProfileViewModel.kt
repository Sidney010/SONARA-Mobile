package com.example.sonara.features.perfilartista.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.usecase.BuscarUsuarioPorIdUseCase
import com.example.sonara.features.perfilartista.model.ArtistProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistProfileViewModel @Inject constructor(
    private val buscarUsuarioPorIdUseCase: BuscarUsuarioPorIdUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistProfileUiState())
    val uiState: StateFlow<ArtistProfileUiState> = _uiState.asStateFlow()

    init {
        loadPerfil()
    }

    fun loadPerfil() {
        viewModelScope.launch {
            val userId = tokenManager.getUserId() ?: run {
                _uiState.update { it.copy(errorMessage = "Usuário não autenticado") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = buscarUsuarioPorIdUseCase(userId)) {
                is AppResult.Success -> {
                    _uiState.update {
                        it.copy(perfil = result.data, isLoading = false)
                    }
                }
                is AppResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading    = false,
                            errorMessage = result.exception.message ?: "Erro ao carregar perfil"
                        )
                    }
                }
            }
        }
    }

    fun onToggleEditing() {
        _uiState.update { it.copy(isEditing = !it.isEditing) }
    }

    // Estrutura preparada para PUT futuro
    fun onSaveChanges() {
        // TODO: implementar PUT /usuario/{id} quando o backend disponibilizar
        viewModelScope.launch {
            _uiState.update { it.copy(successMessage = "Perfil atualizado com sucesso!") }
        }
    }

    fun onClearMessages() {
        _uiState.update { it.copy(successMessage = null, errorMessage = null) }
    }
}