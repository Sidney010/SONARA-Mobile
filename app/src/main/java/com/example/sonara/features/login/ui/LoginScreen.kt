package com.example.sonara.features.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.features.login.components.LoginCard
import com.example.sonara.features.login.event.LoginEvent
import com.example.sonara.features.login.viewmodel.LoginViewModel
import com.example.sonara.features.trocarrsenha.event.RedefinedPasswordEvent

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToRecoverPassword: () -> Unit,
    onNavigateSignUp: () -> Unit
) {

    val uiState by viewModel.uiState
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {

                is LoginEvent.NavigateToRecoverPassword -> {
                   onNavigateToRecoverPassword()
                }

                is LoginEvent.NavigateToSignUp -> {
                    onNavigateSignUp()
                }

                is LoginEvent.ShowError -> {
                    // mostrar snackbar futuramente
                }
            }
        }
    }

    ScreenContainer {

        SonaraLogo()

        LoginCard(
            email = uiState.email.value,
            password = uiState.password.value,
            emailError = uiState.email.error,

            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,

            onLoginClick = viewModel::onLoginClick,
            onSignUpClick = viewModel::onSignUpClick,
            onForgotClick = viewModel::onForgotClick
        )
    }
}


