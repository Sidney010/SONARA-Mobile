package com.example.sonara.features.cadastrar.components.signupcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.sonara.core.ui.components.AppButton
import com.example.sonara.core.ui.components.AppTextField
import com.example.sonara.domain.model.TipoRedeSocial
import com.example.sonara.features.cadastrar.model.RedeSocialDraft

// ---------------------------------------------------------------------------
// Mapeamento de nome → ícone Material
// A comparação é feita em UPPERCASE para ser case-insensitive (vem do backend).
// Para redes não mapeadas, usamos o ícone genérico "Language" como fallback.
// ---------------------------------------------------------------------------
private fun iconForTipoRedeSocial(nome: String): ImageVector {
    return when (nome.uppercase()) {
        "INSTAGRAM"  -> Icons.Default.PhotoCamera
        "YOUTUBE"    -> Icons.Default.OndemandVideo
        "TIKTOK"     -> Icons.Default.MusicNote
        "FACEBOOK"   -> Icons.Default.People
        "TWITTER",
        "X"          -> Icons.Default.Email          // sem ícone nativo para X/Twitter no Material
        "LINKEDIN"   -> Icons.Default.People
        "SOUNDCLOUD" -> Icons.Default.MusicNote
        "SPOTIFY"    -> Icons.Default.MusicNote
        else         -> Icons.Default.Language       // fallback genérico
    }
}

// ---------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedeSocialSection(
    redesSociais: List<RedeSocialDraft>,
    tiposDisponiveis: List<TipoRedeSocial>,
    onAdd: () -> Unit,
    onRemove: (Int) -> Unit,
    onLinkChange: (Int, String) -> Unit,
    onTipoChange: (Int, TipoRedeSocial) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            "Redes Sociais (Opcional)",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        redesSociais.forEachIndexed { index, rede ->
            RedeSocialItem(
                rede = rede,
                tiposDisponiveis = tiposDisponiveis,
                onRemove = { onRemove(index) },
                onLinkChange = { onLinkChange(index, it) },
                onTipoChange = { onTipoChange(index, it) }
            )
        }


        AppButton(
            onClick = onAdd,
            modifier = Modifier.fillMaxWidth(),
            color = Color(250, 197, 146, 255),
            text = "+ Adicionar Rede Social",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RedeSocialItem(
    rede: RedeSocialDraft,
    tiposDisponiveis: List<TipoRedeSocial>,
    onRemove: () -> Unit,
    onLinkChange: (String) -> Unit,
    onTipoChange: (TipoRedeSocial) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = rede.tipo?.nome ?: "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(
                            "Tipo",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    },
                    // Ícone leading: aparece assim que um tipo é selecionado
                    leadingIcon = rede.tipo?.let { tipo ->
                        {
                            Icon(
                                imageVector = iconForTipoRedeSocial(tipo.nome),
                                contentDescription = tipo.nome,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    tiposDisponiveis.forEach { tipo ->
                        DropdownMenuItem(
                            // Ícone no menu também, para o usuário reconhecer visualmente
                            leadingIcon = {
                                Icon(
                                    imageVector = iconForTipoRedeSocial(tipo.nome),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            text = { Text(tipo.nome) },
                            onClick = {
                                onTipoChange(tipo)
                                expanded = false
                            }
                        )
                    }
                }
            }

            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remover",
                    tint = Color.Red.copy(alpha = 0.7f)
                )
            }
        }

        AppTextField(
            value = rede.link,
            onValueChange = onLinkChange,
            placeholder = "Link da rede social (ex: instagram.com/usuario)"
        )
        Spacer(Modifier.height(4.dp))
    }
}