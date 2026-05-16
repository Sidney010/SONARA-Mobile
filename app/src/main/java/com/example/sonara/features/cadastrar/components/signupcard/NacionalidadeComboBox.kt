package com.example.sonara.features.cadastrar.components.signupcard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sonara.domain.model.Nacionalidade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NacionalidadeComboBox(
    selected: Nacionalidade?,
    opcoes: List<Nacionalidade>,
    onSelectedChange: (Nacionalidade) -> Unit,
    isError: Boolean      = false,
    errorMessage: String? = null,
    modifier: Modifier    = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value         = selected?.nome ?: "",
                onValueChange = {},
                readOnly      = true,
                placeholder   = { Text("Nacionalidade", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
                trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier      = Modifier.fillMaxWidth().menuAnchor(),
                isError       = isError,
                colors        = TextFieldDefaults.colors(
                    focusedContainerColor   = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor        = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor      = MaterialTheme.colorScheme.onSurface,
                    focusedIndicatorColor   = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                    unfocusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                )
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                opcoes.forEach { nac ->
                    DropdownMenuItem(
                        text    = { Text(nac.nome) },
                        onClick = { onSelectedChange(nac); expanded = false }
                    )
                }
            }
        }
        if (isError && errorMessage != null) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 4.dp))
        }
    }
}
