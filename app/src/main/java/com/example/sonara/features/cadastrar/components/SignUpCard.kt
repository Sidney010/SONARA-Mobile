package com.example.sonara.features.cadastrar.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sonara.core.ui.components.AppButton
import com.example.sonara.core.ui.components.AppCard
import com.example.sonara.core.ui.components.AppCardHeader
import com.example.sonara.core.ui.components.AppPasswordField
import com.example.sonara.core.ui.components.AppTextField
import com.example.sonara.core.ui.mask.CpfVisualTransformation
import com.example.sonara.domain.model.UserType
import com.example.sonara.features.cadastrar.components.signupcard.UserSelectorRadioButton

@Composable
fun SignUpCard(

    nome: String,
    nomeError: String?,
    onNomeChange: (String) -> Unit,

    cpf: String,
    cpfError: String?,
    onCpfChange: (String) -> Unit,

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

    userType: UserType?,
    onUserTypeChange: (UserType) -> Unit,

    ) {

    AppCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        AppCardHeader("Cadastro")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(0.dp,0.dp,0.dp,5.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            AppTextField(
                value = nome,
                onValueChange = onNomeChange,
                placeholder = "Nome",
                isError = nomeError != null,
                errorMessage = nomeError
            )

            AppTextField(
                value = cpf,
                onValueChange = { input ->
                    val filtered = input.filter { it.isDigit() }

                    if (filtered.length <= 11) {
                        onCpfChange(filtered)
                    }
                },
                placeholder = "CPF",
                visualTransformation = CpfVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                isError = cpfError != null,
                errorMessage = cpfError
            )

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
                placeholder = "Confirmar email",
                isError = emailAgainError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
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

            UserSelectorRadioButton(
                selected = userType,
                onSelectedChange = onUserTypeChange
            )
            AppButton(
                modifier = Modifier.fillMaxWidth(0.7f),
                text = "Cadastrar-se",
                onClick = {/*TODO*/}
            )
        }
    }
}