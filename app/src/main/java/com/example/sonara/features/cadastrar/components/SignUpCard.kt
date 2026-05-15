package com.example.sonara.features.cadastrar.components

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import com.example.sonara.domain.model.GeneroMusical
import com.example.sonara.domain.model.Nacionalidade
import com.example.sonara.domain.model.UserType
import com.example.sonara.features.cadastrar.components.signupcard.DatePickerField
import com.example.sonara.features.cadastrar.components.signupcard.GenderComboBox
import com.example.sonara.features.cadastrar.components.signupcard.GeneroMusicalMultiSelect
import com.example.sonara.features.cadastrar.components.signupcard.NacionalidadeComboBox
import com.example.sonara.features.cadastrar.components.signupcard.UserProfileImagePicker
import com.example.sonara.features.cadastrar.components.signupcard.UserTypeSingleSelector

@Composable
fun SignUpCard(
    // ── Dados pessoais ────────────────────────────────────────────────────
    nome: String, nomeError: String?, onNomeChange: (String) -> Unit,
    cpf: String, cpfError: String?, onCpfChange: (String) -> Unit,
    dataNascimento: String, dataNascimentoError: String?, onDataNascimentoChange: (String) -> Unit,
    telefone: String, onTelefoneChange: (String) -> Unit,

    // ── Tipo de usuário (único) ───────────────────────────────────────────
    userType: UserType?, userTypeError: String?, onUserTypeChange: (UserType) -> Unit,

    // ── Gênero da pessoa ──────────────────────────────────────────────────
    gender: Gender?, genderError: String?, onGenderChange: (Gender) -> Unit,

    // ── Nacionalidade ─────────────────────────────────────────────────────
    nacionalidade: Nacionalidade?, nacionalidadeError: String?,
    nacionalidades: List<Nacionalidade>, onNacionalidadeChange: (Nacionalidade) -> Unit,

    // ── Gêneros musicais ──────────────────────────────────────────────────
    generosMusicaisDisponiveis: List<GeneroMusical>,
    generosMusicaisSelected: Set<Int>, generosMusicaisError: String?,
    onGeneroMusicalToggle: (Int) -> Unit,

    // ── Email e senha ─────────────────────────────────────────────────────
    email: String, emailAgain: String, emailError: String?, emailAgainError: String?,
    onEmailChange: (String) -> Unit, onEmailAgainChange: (String) -> Unit,
    password: String, passwordAgain: String, passwordError: String?, passwordAgainError: String?,
    onPasswordChange: (String) -> Unit, onPasswordAgainChange: (String) -> Unit,

    // ── Dados artísticos ──────────────────────────────────────────────────
    nomeArtistico: String, onNomeArtisticoChange: (String) -> Unit,
    descricao: String, onDescricaoChange: (String) -> Unit,

    // ── Foto ──────────────────────────────────────────────────────────────
    profileImageUri: Uri?, profileImageError: String?, onImageClick: () -> Unit,

    // ── Endereço ──────────────────────────────────────────────────────────
    cep: String, onCepChange: (String) -> Unit,
    rua: String, onRuaChange: (String) -> Unit,
    bairro: String, onBairroChange: (String) -> Unit,
    cidade: String, onCidadeChange: (String) -> Unit,
    uf: String, onUfChange: (String) -> Unit,
    numero: String, onNumeroChange: (String) -> Unit,
    complemento: String, onComplementoChange: (String) -> Unit,
    isLoadingCep: Boolean = false,

    // ── Ação ──────────────────────────────────────────────────────────────
    isLoading: Boolean = false,
    onRegisterClick: () -> Unit,
) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        AppCardHeader("Cadastro")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(0.dp, 0.dp, 0.dp, 8.dp),
            verticalArrangement   = Arrangement.spacedBy(20.dp),
            horizontalAlignment   = Alignment.CenterHorizontally
        ) {
            // ── Foto de perfil ────────────────────────────────────────────
            UserProfileImagePicker(
                imageUri = profileImageUri,
                error    = profileImageError,
                onClick  = onImageClick
            )

            // ── Nome ──────────────────────────────────────────────────────
            AppTextField(
                value = nome, onValueChange = onNomeChange,
                placeholder = "Nome completo *",
                isError = nomeError != null, errorMessage = nomeError
            )

            // ── CPF ───────────────────────────────────────────────────────
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

            // ── Data de nascimento (calendar picker) ─────────────────────
            DatePickerField(
                value = dataNascimento,
                onDateSelected = onDataNascimentoChange,
                isError = dataNascimentoError != null,
                errorMessage = dataNascimentoError
            )

            // ── Telefone ──────────────────────────────────────────────────
            AppTextField(
                value = telefone, onValueChange = onTelefoneChange,
                placeholder = "Telefone",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            // ── Tipo de usuário (radio button único) ──────────────────────
            UserTypeSingleSelector(
                selected = userType,
                onSelectedChange = onUserTypeChange,
                isError = userTypeError != null,
                errorMessage = userTypeError
            )

            // ── Gênero da pessoa ──────────────────────────────────────────
            GenderComboBox(
                selected = gender, onSelectedChange = onGenderChange,
                isError = genderError != null, errorMessage = genderError
            )

            // ── Nacionalidade ─────────────────────────────────────────────
            NacionalidadeComboBox(
                selected = nacionalidade,
                opcoes   = nacionalidades,
                onSelectedChange = onNacionalidadeChange,
                isError = nacionalidadeError != null,
                errorMessage = nacionalidadeError
            )

            // ── Gêneros musicais ──────────────────────────────────────────
            GeneroMusicalMultiSelect(
                generos  = generosMusicaisDisponiveis,
                selected = generosMusicaisSelected,
                onToggle = onGeneroMusicalToggle,
                isError  = generosMusicaisError != null,
                errorMessage = generosMusicaisError
            )

            // ── Nome artístico ────────────────────────────────────────────
            AppTextField(
                value = nomeArtistico, onValueChange = onNomeArtisticoChange,
                placeholder = "Nome artístico"
            )

            // ── Descrição ─────────────────────────────────────────────────
            AppTextField(
                value = descricao, onValueChange = onDescricaoChange,
                placeholder = "Descrição / Bio"
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            // ── Email ─────────────────────────────────────────────────────
            AppTextField(
                value = email, onValueChange = onEmailChange,
                placeholder = "Email *",
                isError = emailError != null, errorMessage = emailError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            AppTextField(
                value = emailAgain, onValueChange = onEmailAgainChange,
                placeholder = "Confirmar email *",
                isError = emailAgainError != null, errorMessage = emailAgainError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // ── Senha ─────────────────────────────────────────────────────
            AppPasswordField(
                value = password, onValueChange = onPasswordChange,
                isError = passwordError != null, errorMessage = passwordError
            )
            AppPasswordField(
                value = passwordAgain, onValueChange = onPasswordAgainChange,
                placeholder = "Confirmar senha *",
                isError = passwordAgainError != null, errorMessage = passwordAgainError
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            // ── Endereço ──────────────────────────────────────────────────
            Text("Endereço", style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))

            // CEP — ao completar 8 dígitos busca automaticamente
            AppTextField(
                value = cep, onValueChange = onCepChange,
                placeholder = "CEP *",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingContent = if (isLoadingCep) {
                    { CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp) }
                } else null
            )
            AppTextField(value = rua, onValueChange = onRuaChange, placeholder = "Rua")
            AppTextField(value = bairro, onValueChange = onBairroChange, placeholder = "Bairro")

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppTextField(value = cidade, onValueChange = onCidadeChange, placeholder = "Cidade", modifier = Modifier.weight(2f))
                AppTextField(value = uf, onValueChange = onUfChange, placeholder = "UF", modifier = Modifier.weight(1f))
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppTextField(value = numero, onValueChange = onNumeroChange, placeholder = "Número", modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                AppTextField(value = complemento, onValueChange = onComplementoChange, placeholder = "Complemento", modifier = Modifier.weight(2f))
            }

            // ── Botão de cadastro ─────────────────────────────────────────
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                AppButton(
                    modifier  = Modifier.fillMaxWidth(0.7f),
                    text      = "Cadastrar-se",
                    onClick   = onRegisterClick
                )
            }
        }
    }
}