package com.example.sonara.features.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sonara.core.theme.LocalGradients
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.features.login.components.LoginCard
import com.example.sonara.features.login.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {
    val gradients = LocalGradients.current
    val uiState by viewModel.uiState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradients.primaryBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp, 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            SonaraLogo()

            LoginCard(
                email = uiState.email,
                password = uiState.password,

                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,

                onLoginClick = viewModel::onLoginClick,
                onSignUpClick = { /* navegar */ },
                onForgotClick = { /* navegar */ }
            )
        }
    }
}


