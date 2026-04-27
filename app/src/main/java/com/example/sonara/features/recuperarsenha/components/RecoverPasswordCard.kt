package com.example.sonara.features.recuperarsenha.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.sonara.core.ui.components.AppButton
import com.example.sonara.core.ui.components.AppCard
import com.example.sonara.core.ui.components.AppCardHeader
import com.example.sonara.core.ui.components.AppTextField

@Composable
fun RecoverPasswordCard(
    email: String,
    emailAgain: String,
    emailError: String?,
    emailAgainError: String?,
    onEmailChange: (String) -> Unit,
    onEmailAgainChange: (String) -> Unit,
    onRecoverPasswordClick: () -> Unit,
){
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AppCardHeader("Recuperação de Senha")
        AppTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = "Email",
            isError = emailError != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            errorMessage = emailError
        )
        AppTextField(
            value = emailAgain,
            onValueChange = onEmailAgainChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            placeholder = "Confirmar email",
            isError = emailAgainError != null,
            errorMessage = emailAgainError
        )
        AppButton(
            modifier = Modifier.fillMaxWidth(0.7f),
            text = "Recuperar Senha",
            onClick = onRecoverPasswordClick
        )
    }
}