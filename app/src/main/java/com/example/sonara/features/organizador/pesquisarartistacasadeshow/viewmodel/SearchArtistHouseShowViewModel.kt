package com.example.sonara.features.organizador.pesquisarartistacasadeshow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.data.remote.dto.response.usuario.ArtistaDto
import com.example.sonara.domain.usecase.GetArtistasUseCase
import com.example.sonara.core.common.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchArtistHouseShowUiState(
    val userName: String = "Organizador",
    val userRole: String = "ORGANIZADOR",
    val avatarUrl: String? = null,
    val isLoggedIn: Boolean = false,
    val artists: List<ArtistaDto> = emptyList(),
    val filteredArtists: List<ArtistaDto> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)

@HiltViewModel
class SearchArtistHouseShowViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val getArtistasUseCase: GetArtistasUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchArtistHouseShowUiState())
    val uiState: StateFlow<SearchArtistHouseShowUiState> = _uiState.asStateFlow()

    init {
        observeSession()
        loadArtists()
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
                        userName = name ?: "Organizador",
                        userRole = type ?: "ORGANIZADOR",
                        avatarUrl = photo,
                        isLoggedIn = !token.isNullOrBlank()
                    )
                }
            }.collect {}
        }
    }

    fun loadArtists() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getArtistasUseCase()) {
                is AppResult.Success -> {
                    _uiState.update { 
                        it.copy(
                            artists = result.data,
                            filteredArtists = filterArtists(result.data, it.searchQuery),
                            isLoading = false 
                        ) 
                    }
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { 
            val filtered = filterArtists(it.artists, query)
            it.copy(searchQuery = query, filteredArtists = filtered)
        }
    }

    private fun filterArtists(artists: List<ArtistaDto>, query: String): List<ArtistaDto> {
        if (query.isBlank()) return artists
        return artists.filter {
            it.nomeArtistico?.contains(query, ignoreCase = true) == true ||
            it.nome.contains(query, ignoreCase = true) ||
            it.genero?.contains(query, ignoreCase = true) == true ||
            it.cidade?.contains(query, ignoreCase = true) == true
        }
    }
}
