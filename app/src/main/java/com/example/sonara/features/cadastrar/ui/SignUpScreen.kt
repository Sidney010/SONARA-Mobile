package com.example.sonara.features.cadastrar.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.features.cadastrar.components.SignUpCard
import com.example.sonara.features.cadastrar.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel()
) {
    val uiState by viewModel.uiState

    ScreenContainer {
        SonaraLogo()

        SignUpCard(

            nome = uiState.nome.value,
            nomeError = uiState.nome.error,

            email = uiState.email.value,
            emailAgain = uiState.emailAgain.value,

            emailError = uiState.email.error,
            emailAgainError = uiState.emailAgain.error,

            password = uiState.password.value,
            passwordAgain = uiState.passwordAgain.value,

            passwordError = uiState.password.error,
            passwordAgainError = uiState.passwordAgain.error,

            onPasswordChange = viewModel::onPasswordChange,
            onPasswordAgainChange = viewModel::onPasswordAgainChange,

            onEmailChange = viewModel::onEmailChange,
            onEmailAgainChange = viewModel::onEmailAgainChange,

            onNomeChange = viewModel::onNomeChange,
            userType = uiState.userType.value,
            onUserTypeChange = viewModel::onUserTypeChange
        )

    }
}