package com.example.sonara.features.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.sonara.core.ui.components.AppButton
import com.example.sonara.core.ui.components.AppCard
import com.example.sonara.core.ui.components.AppCardHeader
import com.example.sonara.core.ui.components.AppPasswordField
import com.example.sonara.core.ui.components.AppTextField
import com.example.sonara.features.login.components.logincard.ForgotPasswordRow
import com.example.sonara.features.login.components.logincard.SignUpRow

@Composable
fun LoginCard(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    emailError: String?,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        AppCardHeader("Bem vindo de volta!")

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
        AppPasswordField(
            value = password,
            onValueChange = onPasswordChange,
        )
        AppButton(
            text = "Entrar",
            onClick = onLoginClick
        )

        SignUpRow(onClick = onSignUpClick )

        ForgotPasswordRow(onClick = onForgotClick)
    }
}