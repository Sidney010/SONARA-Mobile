package com.example.sonara.features.login.ui

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.features.login.components.LoginCard
import com.example.sonara.features.login.event.LoginEvent
import com.example.sonara.features.login.viewmodel.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRecoverPassword: () -> Unit,
    onNavigateSignUp: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val uiState           = viewModel.uiState.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is LoginEvent.NavigateToRecoverPassword -> onNavigateToRecoverPassword()
                is LoginEvent.NavigateToSignUp          -> onNavigateSignUp()
                is LoginEvent.NavigateToHome            -> onLoginSuccess()
                is LoginEvent.ShowError                 -> {
                    snackbarHostState.showSnackbar(event.message, duration = SnackbarDuration.Short)
                }
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { _ ->
        ScreenContainer {
            SonaraLogo()
            LoginCard(
                email           = uiState.email.value,
                password        = uiState.password.value,
                emailError      = uiState.email.error,
                passwordError   = uiState.password.error,
                isLoading       = uiState.isLoading,
                onEmailChange   = viewModel::onEmailChange,
                onPasswordChange= viewModel::onPasswordChange,
                onLoginClick    = viewModel::onLoginClick,
                onSignUpClick   = viewModel::onSignUpClick,
                onForgotClick   = viewModel::onForgotClick
            )
        }
    }
}