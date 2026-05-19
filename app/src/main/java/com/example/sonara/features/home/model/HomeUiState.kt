package com.example.sonara.features.home.model
import com.example.sonara.domain.model.Evento

data class HomeUiState(
    // ── Sessão ────────────────────────────────────────────────────────────────
    val userName: String      = "Anônimo",
    val userRole: String      = "Usuário",
    val avatarUrl: String?    = null,
    val isLoggedIn: Boolean   = false,

    // ── Eventos ───────────────────────────────────────────────────────────────
    val eventosOriginais: List<Evento> = emptyList(),   // lista completa da API
    val eventosFiltrados: List<Evento> = emptyList(),   // lista após filtro + search

    // ── Pesquisa e filtro ─────────────────────────────────────────────────────
    val searchQuery: String        = "",
    val filtroAtivo: EventoFiltro  = EventoFiltro.PADRAO,
    val showFilterSheet: Boolean   = false,

    // ── Controle de UI ────────────────────────────────────────────────────────
    val isLoading: Boolean    = false,
    val errorMessage: String? = null
)