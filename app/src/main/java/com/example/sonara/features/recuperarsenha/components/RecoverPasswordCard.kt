package com.example.sonara.features.recuperarsenha.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sonara.core.ui.components.AppButton
import com.example.sonara.core.ui.components.AppCard
import com.example.sonara.core.ui.components.AppCardHeader
import com.example.sonara.core.ui.components.AppTextField

@Composable
fun RecoverPasswordCard(
    email: String,
    emailAgain: String,
    isEmailValid: Boolean,
    isEmailAgainValid: Boolean,
    errorMessage: String?,
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
            isError = !isEmailValid
        )

        AppTextField(
            value = emailAgain,
            onValueChange = onEmailAgainChange,
            placeholder = "Confirmar email",
            isError = !isEmailAgainValid
        )
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        AppButton(
            modifier = Modifier.fillMaxWidth(0.7f),
            text = "Recuperar Senha",
            onClick = onRecoverPasswordClick
        )
    }
}