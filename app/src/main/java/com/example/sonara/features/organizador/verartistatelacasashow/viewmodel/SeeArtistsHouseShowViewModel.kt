package com.example.sonara.features.organizador.verartistatelacasashow.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.common.AppResult
import com.example.sonara.data.remote.dto.response.usuario.ArtistaDto
import com.example.sonara.domain.usecase.GetArtistasUseCase
import com.example.sonara.core.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.*

data class SeeArtistsHouseShowUiState(
    val userName: String = "Organizador",
    val userRole: String = "ORGANIZADOR",
    val avatarUrl: String? = null,
    val isLoggedIn: Boolean = false,
    val artists: List<ArtistaDto> = emptyList(),
    val filteredArtists: List<ArtistaDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val userLocation: Location? = null,
    val isLocationFilterEnabled: Boolean = false
)

@HiltViewModel
class SeeArtistsHouseShowViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val getArtistasUseCase: GetArtistasUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeeArtistsHouseShowUiState())
    val uiState: StateFlow<SeeArtistsHouseShowUiState> = _uiState.asStateFlow()

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
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getArtistasUseCase()) {
                is AppResult.Success -> {
                    _uiState.update { 
                        it.copy(
                            artists = result.data,
                            filteredArtists = applyFilters(result.data, it.searchQuery, it.userLocation, it.isLocationFilterEnabled),
                            isLoading = false 
                        ) 
                    }
                }
                is AppResult.Error -> {
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            errorMessage = result.exception.message ?: "Erro ao carregar artistas" 
                        ) 
                    }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { 
            val filtered = applyFilters(it.artists, query, it.userLocation, it.isLocationFilterEnabled)
            it.copy(searchQuery = query, filteredArtists = filtered)
        }
    }

    fun onLocationUpdate(location: Location) {
        _uiState.update {
            val filtered = applyFilters(it.artists, it.searchQuery, location, it.isLocationFilterEnabled)
            it.copy(userLocation = location, filteredArtists = filtered)
        }
    }

    fun toggleLocationFilter(enabled: Boolean) {
        _uiState.update {
            val filtered = applyFilters(it.artists, it.searchQuery, it.userLocation, enabled)
            it.copy(isLocationFilterEnabled = enabled, filteredArtists = filtered)
        }
    }

    private fun applyFilters(
        artists: List<ArtistaDto>,
        query: String,
        location: Location?,
        locationFilter: Boolean
    ): List<ArtistaDto> {
        var filtered = artists

        if (query.isNotBlank()) {
            filtered = filtered.filter {
                it.nomeArtistico?.contains(query, ignoreCase = true) == true ||
                it.genero?.contains(query, ignoreCase = true) == true ||
                it.cidade?.contains(query, ignoreCase = true) == true
            }
        }

        if (locationFilter && location != null) {
            // Filter by proximity (e.g., within 50km) and sort by distance
            filtered = filtered.filter { artist ->
                if (artist.latitude != null && artist.longitude != null) {
                    val distance = calculateDistance(
                        location.latitude, location.longitude,
                        artist.latitude, artist.longitude
                    )
                    distance <= 50.0 // 50km radius
                } else {
                    false
                }
            }.sortedBy { artist ->
                calculateDistance(
                    location.latitude, location.longitude,
                    artist.latitude!!, artist.longitude!!
                )
            }
        }

        return filtered
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371 // Earth radius in km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }
}
