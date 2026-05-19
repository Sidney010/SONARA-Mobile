package com.example.sonara.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Evento
import com.example.sonara.domain.usecase.ListarEventosUseCase
import com.example.sonara.features.home.model.EventoFiltro
import com.example.sonara.features.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val listarEventosUseCase: ListarEventosUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeSession()
        loadEventos()
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Sessão — observa mudanças no TokenManager de forma reativa
    // ─────────────────────────────────────────────────────────────────────────

    private fun observeSession() {
        viewModelScope.launch {
            combine(
                tokenManager.userName,
                tokenManager.userType,
                tokenManager.token
            ) { name, type, token ->
                Triple(name, type, token)
            }.collect { (name, type, token) ->
                _uiState.update {
                    it.copy(
                        userName  = name ?: "Anônimo",
                        userRole  = type ?: "Usuário",
                        isLoggedIn = !token.isNullOrBlank()
                    )
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Carregamento de eventos
    // ─────────────────────────────────────────────────────────────────────────

    fun loadEventos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = listarEventosUseCase()) {
                is AppResult.Success -> {
                    val eventos = result.data
                    _uiState.update {
                        it.copy(
                            eventosOriginais = eventos,
                            isLoading        = false
                        )
                    }
                    aplicarFiltroEBusca()
                }
                is AppResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading    = false,
                            errorMessage = result.exception.message ?: "Erro ao carregar eventos"
                        )
                    }
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Pesquisa e filtros
    // ─────────────────────────────────────────────────────────────────────────

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        aplicarFiltroEBusca()
    }

    fun onFiltroChange(filtro: EventoFiltro) {
        _uiState.update { it.copy(filtroAtivo = filtro, showFilterSheet = false) }
        aplicarFiltroEBusca()
    }

    fun onToggleFilterSheet() {
        _uiState.update { it.copy(showFilterSheet = !it.showFilterSheet) }
    }

    fun onDismissFilterSheet() {
        _uiState.update { it.copy(showFilterSheet = false) }
    }

    private fun aplicarFiltroEBusca() {
        val state   = _uiState.value
        val query   = state.searchQuery.trim().lowercase()
        val eventos = state.eventosOriginais

        // 1. Filtragem por texto
        val filtrados = if (query.isBlank()) eventos else eventos.filter { e ->
            e.nome.lowercase().contains(query)      ||
                    e.local?.lowercase()?.contains(query) == true ||
                    e.cidade?.lowercase()?.contains(query) == true ||
                    e.artista?.lowercase()?.contains(query) == true ||
                    e.descricao?.lowercase()?.contains(query) == true
        }

        // 2. Ordenação / filtro por tipo
        val ordenados: List<Evento> = when (state.filtroAtivo) {
            EventoFiltro.PADRAO -> filtrados

            EventoFiltro.POR_DATA ->
                filtrados.sortedWith(compareBy(nullsLast()) { it.data })

            EventoFiltro.POR_HORA ->
                filtrados.sortedWith(compareBy(nullsLast()) { it.horaInicio })

            EventoFiltro.POR_ARTISTA ->
                // Exibe apenas eventos com artista definido, artistas primeiro
                filtrados.sortedByDescending { it.artista != null }

            EventoFiltro.POR_LOCALIZACAO ->
                // Com lat/lng seria geolocalização real; por ora ordena por cidade
                filtrados.sortedWith(compareBy(nullsLast()) { it.cidade })
        }

        _uiState.update { it.copy(eventosFiltrados = ordenados) }
    }
}