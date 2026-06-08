package com.example.sonara.features.cadastrar.components.steps

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sonara.core.ui.components.AppTextField
import com.example.sonara.core.ui.mask.CpfVisualTransformation
import com.example.sonara.core.ui.mask.TelefoneVisualTransformation
import com.example.sonara.domain.model.Gender
import com.example.sonara.domain.model.Nacionalidade
import com.example.sonara.domain.model.UserType
import com.example.sonara.features.cadastrar.components.signupcard.DatePickerField
import com.example.sonara.features.cadastrar.components.signupcard.GenderComboBox
import com.example.sonara.features.cadastrar.components.signupcard.NacionalidadeComboBox
import com.example.sonara.features.cadastrar.components.signupcard.UserProfileImagePicker
import com.example.sonara.features.cadastrar.components.signupcard.UserTypeSingleSelector

@Composable
fun PersonalDataStep(
    nome: String, nomeError: String?, onNomeChange: (String) -> Unit,
    cpf: String, cpfError: String?, onCpfChange: (String) -> Unit,
    dataNascimento: String, dataNascimentoError: String?, onDataNascimentoChange: (String) -> Unit,
    telefone: String, onTelefoneChange: (String) -> Unit,
    userType: UserType?, userTypeError: String?, onUserTypeChange: (UserType) -> Unit,
    gender: Gender?, genderError: String?, onGenderChange: (Gender) -> Unit,
    nacionalidade: Nacionalidade?, nacionalidadeError: String?,
    nacionalidades: List<Nacionalidade>, onNacionalidadeChange: (Nacionalidade) -> Unit,
    profileImageUri: Uri?, profileImageError: String?, onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Foto de perfil
        UserProfileImagePicker(
            imageUri = profileImageUri,
            error = profileImageError,
            onClick = onImageClick
        )

        // Nome
        AppTextField(
            value = nome, onValueChange = onNomeChange,
            placeholder = "Nome completo *",
            isError = nomeError != null, errorMessage = nomeError
        )

        // CPF
        AppTextField(
            value = cpf,
            onValueChange = { input ->
                val filtered = input.filter { it.isDigit() }
                if (filtered.length <= 11) onCpfChange(filtered)
            },
            placeholder = "CPF *",
            visualTransformation = CpfVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = cpfError != null, errorMessage = cpfError
        )

        // Data de nascimento
        DatePickerField(
            value = dataNascimento,
            onDateSelected = onDataNascimentoChange,
            isError = dataNascimentoError != null,
            errorMessage = dataNascimentoError
        )

        // Telefone
        AppTextField(
            value = telefone,
            onValueChange = { input ->
                val digits = input.filter { it.isDigit() }
                if (digits.length <= 11) onTelefoneChange(digits)
            },
            placeholder = "Telefone",
            visualTransformation = TelefoneVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        // Tipo de usuário
        UserTypeSingleSelector(
            selected = userType,
            onSelectedChange = onUserTypeChange,
            isError = userTypeError != null,
            errorMessage = userTypeError
        )

        // Gênero
        GenderComboBox(
            selected = gender, onSelectedChange = onGenderChange,
            isError = genderError != null, errorMessage = genderError
        )

        // Nacionalidade
        NacionalidadeComboBox(
            selected = nacionalidade,
            opcoes = nacionalidades,
            onSelectedChange = onNacionalidadeChange,
            isError = nacionalidadeError != null,
            errorMessage = nacionalidadeError
        )
    }
}
