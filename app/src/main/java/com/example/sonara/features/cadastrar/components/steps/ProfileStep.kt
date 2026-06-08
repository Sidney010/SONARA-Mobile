package com.example.sonara.features.cadastrar.components.steps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sonara.core.ui.components.AppPasswordField
import com.example.sonara.core.ui.components.AppTextField
import com.example.sonara.domain.model.GeneroMusical
import com.example.sonara.domain.model.TipoRedeSocial
import com.example.sonara.domain.model.UserType
import com.example.sonara.features.cadastrar.components.signupcard.GeneroMusicalMultiSelect
import com.example.sonara.features.cadastrar.components.signupcard.RedeSocialSection
import com.example.sonara.features.cadastrar.model.RedeSocialDraft

@Composable
fun ProfileStep(
    email: String, emailAgain: String, emailError: String?, emailAgainError: String?,
    onEmailChange: (String) -> Unit, onEmailAgainChange: (String) -> Unit,
    password: String, passwordAgain: String, passwordError: String?, passwordAgainError: String?,
    onPasswordChange: (String) -> Unit, onPasswordAgainChange: (String) -> Unit,
    userType: UserType?,
    // Campos Artista
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
    modifier: Modifier = Modifier
) {
    val isArtista = userType == UserType.ARTISTA

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Email
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

        // Senha
        AppPasswordField(
            value = password, onValueChange = onPasswordChange,
            isError = passwordError != null, errorMessage = passwordError
        )
        AppPasswordField(
            value = passwordAgain, onValueChange = onPasswordAgainChange,
            placeholder = "Confirmar senha *",
            isError = passwordAgainError != null, errorMessage = passwordAgainError
        )

        // ── Campos exclusivos do ARTISTA ──────────────────────────
        AnimatedVisibility(
            visible = isArtista,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Gêneros musicais
                GeneroMusicalMultiSelect(
                    generos = generosMusicaisDisponiveis,
                    selected = generosMusicaisSelected,
                    onToggle = onGeneroMusicalToggle,
                    isError = generosMusicaisError != null,
                    errorMessage = generosMusicaisError
                )

                // Nome artístico
                AppTextField(
                    value = nomeArtistico,
                    onValueChange = onNomeArtisticoChange,
                    placeholder = "Nome artístico"
                )

                // Descrição / Bio
                AppTextField(
                    value = descricao,
                    onValueChange = onDescricaoChange,
                    placeholder = "Descrição / Bio"
                )

                // Redes Sociais
                RedeSocialSection(
                    redesSociais = redesSociais,
                    onAdd = onAddRedeSocial,
                    onRemove = onRemoveRedeSocial,
                    onLinkChange = onRedeSocialLinkChange,
                    onTipoChange = onRedeSocialTipoChange,
                    tiposDisponiveis = tiposRedesSociais
                )
            }
        }
    }
}
