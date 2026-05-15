package com.example.sonara.features.cadastrar.components.signupcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sonara.domain.model.UserType
import com.example.sonara.domain.model.toDisplayName

@Composable
fun UserTypeSingleSelector(
    selected: UserType?,
    onSelectedChange: (UserType) -> Unit,
    isError: Boolean      = false,
    errorMessage: String? = null,
    modifier: Modifier    = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text  = "Tipo de usuário *",
            style = MaterialTheme.typography.labelMedium,
            color = if (isError) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
        UserType.entries.chunked(2).forEach { row ->
            Row {
                row.forEach { type ->
                    Row(
                        modifier          = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected  = selected == type,
                            onClick   = { onSelectedChange(type) }
                        )
                        Text(type.toDisplayName(), color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
        if (isError && errorMessage != null) {
            Text(errorMessage, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
    }
}