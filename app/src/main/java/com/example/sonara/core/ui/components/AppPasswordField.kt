package com.example.sonara.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.sonara.R

@Composable
fun AppPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    placeholder: String = "Senha",
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    Column (verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                ) },
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                unfocusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
            ),
            visualTransformation = if (visible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        painter = painterResource(if (visible){
                            R.drawable.sonara_logo
                        }else {
                            R.drawable.sonara_logo
                        }),
                        contentDescription = "Visivel / Não visivel",
                        tint = if(visible) Color.Blue else Color.Red

                    )
                }
            }
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}