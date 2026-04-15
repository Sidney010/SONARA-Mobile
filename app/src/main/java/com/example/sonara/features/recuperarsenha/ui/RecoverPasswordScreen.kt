package com.example.sonara.features.recuperarsenha.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.features.recuperarsenha.components.RecoverPasswordCard
import com.example.sonara.features.recuperarsenha.viewmodel.RecoverPasswordViewModel

@Composable
fun RecuperarSenhaScreen(
    viewModel: RecoverPasswordViewModel = viewModel()
) {
    val uiState by viewModel.uiState

    ScreenContainer{

        SonaraLogo()

        RecoverPasswordCard(
            email = uiState.email,
            emailAgain = uiState.emailAgain,

            isEmailValid = uiState.isEmailValid,
            isEmailAgainValid = uiState.isEmailAgainValid,
            errorMessage = uiState.errorMessage,

            onEmailChange = viewModel::onEmailChange,
            onEmailAgainChange = viewModel::onEmailAgainChange,
            onRecoverPasswordClick = viewModel::onRecoverPasswordClick,
        )

    }
}