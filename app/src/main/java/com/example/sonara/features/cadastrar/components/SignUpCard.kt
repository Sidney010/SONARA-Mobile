package com.example.sonara.features.cadastrar.components


import android.R.attr.password
import android.net.Uri
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
import com.example.sonara.domain.model.Gender
import com.example.sonara.domain.model.UserType
import com.example.sonara.features.cadastrar.components.signupcard.GenderComboBox


import com.example.sonara.features.cadastrar.components.signupcard.UserProfileImagePicker
import com.example.sonara.features.cadastrar.components.signupcard.UserSelectorCheckBox

@Composable
fun SignUpCard(

    nome: String,
    nomeError: String?,
    onNomeChange: (String) -> Unit,

    cpf: String,
    cpfError: String?,
    onCpfChange: (String) -> Unit,

    gender: Gender?,
    genderError: String?,
    onGenderChange: (Gender) -> Unit,

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

    userType: Set<UserType>,
    onUserTypeChange: (Set<UserType>) -> Unit,

    profileImageUri: Uri?,
    profileImageError: String?,
    onImageClick: () -> Unit,
    onRegisterClick: () -> Unit,
    cep: String,
    onCepChange: (String) -> Unit,

    rua: String,
    onRuaChange: (String) -> Unit,

    bairro: String,
    onBairroChange: (String) -> Unit,

    cidade: String,
    onCidadeChange: (String) -> Unit,

    uf: String,
    onUfChange: (String) -> Unit,

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
            UserProfileImagePicker(
                imageUri = profileImageUri,
                error = profileImageError,
                onClick = onImageClick
            )

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
            GenderComboBox(
                selected = gender,
                onSelectedChange = onGenderChange,
                isError = genderError != null,
                errorMessage = genderError
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

            UserSelectorCheckBox(
                selected = userType,
                onSelectedChange = onUserTypeChange
            )

            AppTextField(
                value = cep,
                onValueChange = onCepChange,
                placeholder = "CEP",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            AppTextField(
                value = rua,
                onValueChange = onRuaChange,
                placeholder = "Rua"
            )

            AppTextField(
                value = cidade,
                onValueChange = onCidadeChange,
                placeholder = "Cidade"
            )

            AppTextField(
                value = bairro,
                onValueChange = onBairroChange,
                placeholder = "Bairro"
            )

            AppTextField(
                value = uf,
                onValueChange = onUfChange,
                placeholder = "UF"
            )


            AppButton(
                modifier = Modifier.fillMaxWidth(0.7f),
                text = "Cadastrar-se",
                onClick = onRegisterClick
            )
        }
    }
}