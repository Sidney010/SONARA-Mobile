package com.example.sonara.features.cadastrar.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sonara.core.ui.components.AppButton
import com.example.sonara.core.ui.components.AppCard
import com.example.sonara.core.ui.components.AppCardHeader
import com.example.sonara.core.ui.components.AppPasswordField
import com.example.sonara.core.ui.components.AppTextField

@Composable
fun SignUpCard(

    nome: String,
    nomeError: String?,
    onNomeChange: (String) -> Unit,

    email: String,
    emailAgain: String,
    emailError: String?,
    emailAgainError: String?,
    onEmailChange: (String) -> Unit,
    onEmailAgainChange: (String) -> Unit,

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
        AppCardHeader("Cadastro")

        AppTextField(
            value = nome,
            onValueChange = onNomeChange,
            placeholder = "Nome",
            isError = nomeError != null,
            errorMessage = nomeError
        )

        AppTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = "Email",
            isError = emailError != null,
            errorMessage = emailError
        )

        AppTextField(
            value = emailAgain,
            onValueChange = onEmailAgainChange,
            placeholder = "Confirmar email",
            isError = emailAgainError != null,
            errorMessage = emailAgainError
        )

        AppPasswordField(
            value = password,
            onValueChange = onPasswordChange,
            isError = passwordError != null,
            errorMessage = passwordError
        )

        AppPasswordField(
            value = passwordAgain,
            onValueChange = onPasswordAgainChange,
            placeholder = "Confirmar senha",
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