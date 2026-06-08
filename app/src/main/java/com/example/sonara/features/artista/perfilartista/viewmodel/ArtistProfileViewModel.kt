package com.example.sonara.features.artista.perfilartista.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.core.validation.UrlValidator
import com.example.sonara.core.validation.ValidationResult
import com.example.sonara.domain.model.RedeSocial
import com.example.sonara.domain.usecase.BuscarUsuarioPorIdUseCase
import com.example.sonara.domain.usecase.CreateRedeSocialUseCase
import com.example.sonara.domain.usecase.DeleteRedeSocialUseCase
import com.example.sonara.domain.usecase.ListarTiposRedesSociaisUseCase
import com.example.sonara.domain.usecase.UpdateRedeSocialUseCase
import com.example.sonara.features.artista.perfilartista.model.ArtistProfileUiState
import com.example.sonara.features.cadastrar.model.RedeSocialDraft
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ArtistProfileEvent {
    object NavigateToStart : ArtistProfileEvent()
}

@HiltViewModel
class ArtistProfileViewModel @Inject constructor(
    private val buscarUsuarioPorIdUseCase: BuscarUsuarioPorIdUseCase,
    private val listarTiposRedesSociaisUseCase: ListarTiposRedesSociaisUseCase,
    private val createRedeSocialUseCase: CreateRedeSocialUseCase,
    private val updateRedeSocialUseCase: UpdateRedeSocialUseCase,
    private val deleteRedeSocialUseCase: DeleteRedeSocialUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistProfileUiState())
    val uiState: StateFlow<ArtistProfileUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ArtistProfileEvent>()
    val event = _event.asSharedFlow()

    init { loadPerfil() }

    fun loadPerfil() {
        viewModelScope.launch {
            val userId = tokenManager.getUserId() ?: run {
                _uiState.update { it.copy(errorMessage = "Usuário não autenticado") }
                return@launch
            }
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Carrega tipos de redes sociais
            val tiposResult = listarTiposRedesSociaisUseCase()
            if (tiposResult is AppResult.Success) {
                _uiState.update { it.copy(tiposRedesSociais = tiposResult.data) }
            }

            when (val result = buscarUsuarioPorIdUseCase(userId)) {
                is AppResult.Success -> {
                    val perfil = result.data
                    val drafts = perfil.redesSociais.map { rs ->
                        RedeSocialDraft(
                            id = rs.id,
                            link = rs.link,
                            tipo = _uiState.value.tiposRedesSociais.find { it.id == rs.tipoId }
                        )
                    }
                    _uiState.update {
                        it.copy(
                            perfil = perfil,
                            redesSociais = drafts,
                            isLoading = false
                        )
                    }
                }
                is AppResult.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exception.message ?: "Erro ao carregar perfil"
                    )
                }
            }
        }
    }

    fun onAddRedeSocial() {
        _uiState.update {
            it.copy(redesSociais = it.redesSociais + RedeSocialDraft())
        }
    }

    fun onRemoveRedeSocial(index: Int) {
        val currentList = _uiState.value.redesSociais.toMutableList()
        if (index !in currentList.indices) return
        
        val itemToRemove = currentList[index]

        if (itemToRemove.id != null) {
            viewModelScope.launch {
                _uiState.update { it.copy(isSaving = true) }
                when (val result = deleteRedeSocialUseCase(itemToRemove.id)) {
                    is AppResult.Success -> {
                        currentList.removeAt(index)
                        _uiState.update { it.copy(redesSociais = currentList, isSaving = false) }
                    }
                    is AppResult.Error -> {
                        _uiState.update {
                            it.copy(
                                isSaving = false,
                                errorMessage = "Erro ao excluir rede social: ${result.exception.message}"
                            )
                        }
                    }
                }
            }
        } else {
            currentList.removeAt(index)
            _uiState.update { it.copy(redesSociais = currentList) }
        }
    }

    fun onRedeSocialChange(index: Int, draft: RedeSocialDraft) {
        val currentList = _uiState.value.redesSociais.toMutableList()
        if (index in currentList.indices) {
            currentList[index] = draft
            _uiState.update { it.copy(redesSociais = currentList) }
        }
    }

    fun onToggleEditing() {
        _uiState.update { it.copy(isEditing = !it.isEditing) }
    }

    fun onSaveChanges() {
        viewModelScope.launch {
            val userId = tokenManager.getUserId() ?: return@launch
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }

            val drafts = _uiState.value.redesSociais
            val results = drafts.map { draft ->
                val redeSocial = RedeSocial(
                    id = draft.id,
                    link = draft.link,
                    tipoId = draft.tipo?.id ?: 0,
                    usuarioId = userId
                )

                if (draft.link.isNotBlank() && draft.tipo != null) {
                    val validation = UrlValidator.validate(draft.link)
                    if (validation is ValidationResult.Error) {
                        return@launch _uiState.update { it.copy(isSaving = false, errorMessage = "Link inválido: ${draft.link}") }
                    }

                    if (draft.id == null) {
                        createRedeSocialUseCase(redeSocial)
                    } else {
                        updateRedeSocialUseCase(draft.id, redeSocial)
                    }
                } else {
                    AppResult.Success(redeSocial)
                }
            }

            val hasError = results.any { it is AppResult.Error }
            if (hasError) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = "Ocorreu um erro ao salvar algumas redes sociais."
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isEditing = false,
                        successMessage = "Perfil atualizado com sucesso!"
                    )
                }
                loadPerfil() // Recarrega para garantir dados frescos
            }
        }
    }

    fun onClearMessages() {
        _uiState.update { it.copy(successMessage = null, errorMessage = null) }
    }

    // ── Logout ────────────────────────────────────────────────────────
    fun logout() {
        viewModelScope.launch {
            tokenManager.clearSession()
            _event.emit(ArtistProfileEvent.NavigateToStart)
        }
    }
}