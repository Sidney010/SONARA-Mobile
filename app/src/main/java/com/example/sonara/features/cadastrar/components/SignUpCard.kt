package com.example.sonara.features.cadastrar.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sonara.core.ui.components.AppButton
import com.example.sonara.core.ui.components.AppCard
import com.example.sonara.core.ui.components.AppCardHeader
import com.example.sonara.core.ui.components.AppPasswordField

@Composable
fun SignUpCard(
    modifier: Modifier = Modifier,
    password: String,
    passwordAgain: String,
    passwordError: String?,
    passwordAgainError: String?,
    onPasswordChange: (String) -> Unit,
    onPasswordAgainChange: (String) -> Unit,
) {
    AppCard(
        modifier = Modifier.fillMaxWidth()
    ) {

        AppCardHeader("Alterar sua Senha")

        AppPasswordField(
            value = password,
            onValueChange = onPasswordChange,
            isError = passwordError != null,
            errorMessage = passwordError
        )

        AppPasswordField(
            value = passwordAgain,
            onValueChange = onPasswordAgainChange,
            isError = passwordAgainError != null,
            errorMessage = passwordAgainError
        )

        AppButton(
            modifier = Modifier.fillMaxWidth(0.7f),
            text = "Cadastrar-se",
            onClick = {/*TODO*/}
        )
    }
}