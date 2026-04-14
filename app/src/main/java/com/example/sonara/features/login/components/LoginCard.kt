package com.example.sonara.features.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sonara.core.theme.LocalGradients
import com.example.sonara.features.login.components.logincard.AppPasswordField
import com.example.sonara.features.login.components.logincard.AppTextField
import com.example.sonara.features.login.components.logincard.ForgotPasswordRow
import com.example.sonara.features.login.components.logincard.LoginButton
import com.example.sonara.features.login.components.logincard.LoginHeader
import com.example.sonara.features.login.components.logincard.SignUpRow

@Composable
fun LoginCard(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradients = LocalGradients.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(440.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradients.primaryCard)
                .padding(32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LoginHeader("Bem vindo de volta!")

                AppTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    placeholder = "Email",
                )

                AppPasswordField(
                    value = password,
                    onValueChange = onPasswordChange
                )

                LoginButton(
                    text = "Entrar",
                    onClick = onLoginClick
                )

                SignUpRow(onClick = onSignUpClick)

                ForgotPasswordRow(onClick = onForgotClick)
            }
        }
    }
}