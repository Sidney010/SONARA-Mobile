package com.example.sonara.features.trocarrsenha.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.features.trocarrsenha.components.RedefinedPasswordCard
import com.example.sonara.features.trocarrsenha.event.RedefinedPasswordEvent
import com.example.sonara.features.trocarrsenha.viewmodel.RedefinedPasswordViewModel

@Composable
fun RedefinedPasswordScreen(
    viewModel: RedefinedPasswordViewModel = viewModel(),
    onNavigateToLogin: () -> Unit
) {
    // Conceitos importantes para eu não esquecer e para qualquer programador que abrir este código
    // Os dois observam mudanças:

    // State → dados da tela (email, senha)
    val uiState by viewModel.uiState

    // Event → ações únicas (navegar, mostrar snackbar)
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {

                is RedefinedPasswordEvent.NavigateToLogin -> {
                    onNavigateToLogin()
                }

                is RedefinedPasswordEvent.ShowError -> {
                    // mostrar snackbar futuramente
                }
            }
        }
    }

    ScreenContainer {

        SonaraLogo()

        RedefinedPasswordCard(
            password = uiState.password.value,
            passwordAgain = uiState.passwordAgain.value,

            passwordError = uiState.password.error,
            passwordAgainError = uiState.passwordAgain.error,

            onPasswordChange = viewModel::onPasswordChange,
            onPasswordAgainChange = viewModel::onPasswordAgainChange,
            onRedefinedPasswordClick = viewModel::onRedefinedPasswordClick
        )
    }
}