package com.example.sonara.features.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.features.login.components.LoginCard
import com.example.sonara.features.login.viewmodel.LoginViewModel
import com.example.sonara.features.recuperarsenha.viewmodel.RecoverPasswordViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToRecoverPassword: () -> Unit
) {

    val uiState by viewModel.uiState

    ScreenContainer {

        SonaraLogo()

        LoginCard(
            email = uiState.email,
            password = uiState.password,

            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,

            onLoginClick = viewModel::onLoginClick,
            onSignUpClick = { /* TODO navegar */ },
            onForgotClick = onNavigateToRecoverPassword
        )
    }
}


