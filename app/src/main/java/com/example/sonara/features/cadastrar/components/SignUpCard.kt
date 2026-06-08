package com.example.sonara.features.cadastrar.components

import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.SizeTransform
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sonara.core.ui.components.AppButton
import com.example.sonara.core.ui.components.AppCard
import com.example.sonara.core.ui.components.AppCardHeader
import com.example.sonara.domain.model.Gender
import com.example.sonara.domain.model.GeneroMusical
import com.example.sonara.domain.model.Nacionalidade
import com.example.sonara.domain.model.TipoRedeSocial
import com.example.sonara.domain.model.UserType
import com.example.sonara.features.cadastrar.components.steps.AddressStep
import com.example.sonara.features.cadastrar.components.steps.PersonalDataStep
import com.example.sonara.features.cadastrar.components.steps.ProfileStep
import com.example.sonara.features.cadastrar.model.RedeSocialDraft
import com.example.sonara.features.cadastrar.model.SignUpStep

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SignUpCard(
    currentStep: SignUpStep,
    // Dados pessoais
    nome: String, nomeError: String?, onNomeChange: (String) -> Unit,
    cpf: String, cpfError: String?, onCpfChange: (String) -> Unit,
    dataNascimento: String, dataNascimentoError: String?, onDataNascimentoChange: (String) -> Unit,
    telefone: String, onTelefoneChange: (String) -> Unit,

    // Tipo de usuário
    userType: UserType?, userTypeError: String?, onUserTypeChange: (UserType) -> Unit,

    // Gênero
    gender: Gender?, genderError: String?, onGenderChange: (Gender) -> Unit,

    // Nacionalidade
    nacionalidade: Nacionalidade?, nacionalidadeError: String?,
    nacionalidades: List<Nacionalidade>, onNacionalidadeChange: (Nacionalidade) -> Unit,

    // Perfil / Artístico
    email: String, emailAgain: String, emailError: String?, emailAgainError: String?,
    onEmailChange: (String) -> Unit, onEmailAgainChange: (String) -> Unit,
    password: String, passwordAgain: String, passwordError: String?, passwordAgainError: String?,
    onPasswordChange: (String) -> Unit, onPasswordAgainChange: (String) -> Unit,
    generosMusicaisDisponiveis: List<GeneroMusical>,
    generosMusicaisSelected: Set<Int>, generosMusicaisError: String?,
    onGeneroMusicalToggle: (Int) -> Unit,
    nomeArtistico: String, onNomeArtisticoChange: (String) -> Unit,
    descricao: String, onDescricaoChange: (String) -> Unit,
    redesSociais: List<RedeSocialDraft>,
    onAddRedeSocial: () -> Unit,
    onRemoveRedeSocial: (Int) -> Unit,
    onRedeSocialLinkChange: (Int, String) -> Unit,
    onRedeSocialTipoChange: (Int, TipoRedeSocial) -> Unit,
    tiposRedesSociais: List<TipoRedeSocial>,

    // Foto
    profileImageUri: Uri?, profileImageError: String?, onImageClick: () -> Unit,

    // Endereço
    cep: String, cepError: String?, onCepChange: (String) -> Unit,
    rua: String, ruaError: String?, onRuaChange: (String) -> Unit,
    bairro: String, bairroError: String?, onBairroChange: (String) -> Unit,
    cidade: String, cidadeError: String?, onCidadeChange: (String) -> Unit,
    uf: String, ufError: String?, onUfChange: (String) -> Unit,
    numero: String, onNumeroChange: (String) -> Unit,
    complemento: String, onComplementoChange: (String) -> Unit,
    isLoadingCep: Boolean = false,

    // Controle
    isLoading: Boolean = false,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        AppCardHeader("Cadastro")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignUpProgressIndicator(currentStep = currentStep)
            
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedContent(
                    targetState = currentStep,
                    transitionSpec = {
                        if (targetState.ordinal > initialState.ordinal) {
                            (slideInHorizontally { width -> width } + fadeIn())
                                .togetherWith(slideOutHorizontally { width -> -width } + fadeOut())
                        } else {
                            (slideInHorizontally { width -> -width } + fadeIn())
                                .togetherWith(slideOutHorizontally { width -> width } + fadeOut())
                        }.using(
                            SizeTransform(clip = false)
                        )
                    },
                    label = "StepTransition"
                ) { step ->
                    when (step) {
                        SignUpStep.PERSONAL_DATA -> {
                            PersonalDataStep(
                                nome = nome, nomeError = nomeError, onNomeChange = onNomeChange,
                                cpf = cpf, cpfError = cpfError, onCpfChange = onCpfChange,
                                dataNascimento = dataNascimento, dataNascimentoError = dataNascimentoError, onDataNascimentoChange = onDataNascimentoChange,
                                telefone = telefone, onTelefoneChange = onTelefoneChange,
                                userType = userType, userTypeError = userTypeError, onUserTypeChange = onUserTypeChange,
                                gender = gender, genderError = genderError, onGenderChange = onGenderChange,
                                nacionalidade = nacionalidade, nacionalidadeError = nacionalidadeError,
                                nacionalidades = nacionalidades, onNacionalidadeChange = onNacionalidadeChange,
                                profileImageUri = profileImageUri, profileImageError = profileImageError, onImageClick = onImageClick
                            )
                        }
                        SignUpStep.PROFILE_DATA -> {
                            ProfileStep(
                                email = email, emailAgain = emailAgain, emailError = emailError, emailAgainError = emailAgainError,
                                onEmailChange = onEmailChange, onEmailAgainChange = onEmailAgainChange,
                                password = password, passwordAgain = passwordAgain, passwordError = passwordError, passwordAgainError = passwordAgainError,
                                onPasswordChange = onPasswordChange, onPasswordAgainChange = onPasswordAgainChange,
                                userType = userType,
                                generosMusicaisDisponiveis = generosMusicaisDisponiveis,
                                generosMusicaisSelected = generosMusicaisSelected, generosMusicaisError = generosMusicaisError,
                                onGeneroMusicalToggle = onGeneroMusicalToggle,
                                nomeArtistico = nomeArtistico, onNomeArtisticoChange = onNomeArtisticoChange,
                                descricao = descricao, onDescricaoChange = onDescricaoChange,
                                redesSociais = redesSociais, onAddRedeSocial = onAddRedeSocial,
                                onRemoveRedeSocial = onRemoveRedeSocial, onRedeSocialLinkChange = onRedeSocialLinkChange,
                                onRedeSocialTipoChange = onRedeSocialTipoChange, tiposRedesSociais = tiposRedesSociais
                            )
                        }
                        SignUpStep.ADDRESS -> {
                            AddressStep(
                                cep = cep, cepError = cepError, onCepChange = onCepChange,
                                rua = rua, ruaError = ruaError, onRuaChange = onRuaChange,
                                bairro = bairro, bairroError = bairroError, onBairroChange = onBairroChange,
                                cidade = cidade, cidadeError = cidadeError, onCidadeChange = onCidadeChange,
                                uf = uf, ufError = ufError, onUfChange = onUfChange,
                                numero = numero, onNumeroChange = onNumeroChange,
                                complemento = complemento, onComplementoChange = onComplementoChange,
                                isLoadingCep = isLoadingCep
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botões de Navegação
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (currentStep != SignUpStep.PERSONAL_DATA) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = onBackClick
                    ) {
                        androidx.compose.material3.Text("Voltar")
                    }
                }

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterVertically))
                } else {
                    val buttonText = if (currentStep == SignUpStep.ADDRESS) "Cadastrar-se" else "Próximo"
                    AppButton(
                        modifier = Modifier.weight(1f),
                        text = buttonText,
                        onClick = onNextClick
                    )
                }
            }
        }
    }
}
