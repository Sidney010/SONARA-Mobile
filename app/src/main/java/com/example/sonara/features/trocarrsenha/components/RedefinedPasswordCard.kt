package com.example.sonara.features.trocarrsenha.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sonara.core.ui.components.AppButton
import com.example.sonara.core.ui.components.AppCard
import com.example.sonara.core.ui.components.AppCardHeader
import com.example.sonara.core.ui.components.AppPasswordField

@Composable
fun RedefinedPasswordCard(
    password: String,
    passwordAgain: String,
    isPasswordValid: Boolean,
    isPasswordAgainValid: Boolean,
    errorMessage: String?,
    onPasswordChange: (String) -> Unit,
    onPasswordAgainChange: (String) -> Unit,
    onRedefinedPasswordClick: () -> Unit,
){
    AppCard (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AppCardHeader("Alterar sua Senha")
        AppPasswordField(
            value = password,
            onValueChange = onPasswordChange,
            isError = !isPasswordValid,
        )
        AppPasswordField(
            value = passwordAgain,
            onValueChange = onPasswordAgainChange,
            isError = !isPasswordAgainValid
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
            text = "Redefinir Senha",
            onClick = onRedefinedPasswordClick
        )

    }

}