package com.example.sonara.features.plano.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.features.plano.model.PlansUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlansViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlansUiState())
    val uiState: StateFlow<PlansUiState> = _uiState.asStateFlow()

    init {
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
                        userName  = name  ?: "Anônimo",
                        userRole  = type  ?: "Usuário",
                        isLoggedIn = !token.isNullOrBlank()
                    )
                }
            }
        }
    }
}