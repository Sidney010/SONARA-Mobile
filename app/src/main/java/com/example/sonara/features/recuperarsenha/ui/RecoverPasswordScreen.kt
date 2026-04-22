package com.example.sonara.features.recuperarsenha.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.features.recuperarsenha.components.RecoverPasswordCard
import com.example.sonara.features.recuperarsenha.event.RecoverPasswordEvent
import com.example.sonara.features.recuperarsenha.viewmodel.RecoverPasswordViewModel

@Composable
fun RecoverPasswordScreen(
    viewModel: RecoverPasswordViewModel = viewModel(),
    onNavigateToRedefinedPassword: () -> Unit
) {
    val uiState by viewModel.uiState
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {

                is RecoverPasswordEvent.NavigateToRedefinedPassword -> {
                    onNavigateToRedefinedPassword()
                }

                is RecoverPasswordEvent.ShowError -> {
                    // snackbar futuramente
                }
            }
        }
    }

    ScreenContainer{

        SonaraLogo()

        RecoverPasswordCard(
            email = uiState.email.value,
            emailAgain = uiState.emailAgain.value,

            onEmailChange = viewModel::onEmailChange,
            onEmailAgainChange = viewModel::onEmailAgainChange,

            emailError = uiState.email.error,
            emailAgainError = uiState.emailAgain.error,

            onRecoverPasswordClick = viewModel::onRecoverPasswordClick
        )
    }
}