package com.example.sonara.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lint.kotlin.metadata.Visibility

// CORREÇÃO: ícone do olho estava usando sonara_logo para ambos os estados
@Composable
fun AppPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean      = false,
    placeholder: String   = "Senha *",
    errorMessage: String? = null,
    modifier: Modifier    = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value    = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            },
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedContainerColor   = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor        = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor      = MaterialTheme.colorScheme.onSurface,
                cursorColor             = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor   = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                unfocusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
            ),
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        imageVector = if (visible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        contentDescription = if (visible) "Ocultar senha" else "Mostrar senha",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        )
        if (isError && errorMessage != null) {
            Text(
                text     = errorMessage,
                color    = MaterialTheme.colorScheme.error,
                style    = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}