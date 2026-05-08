package com.example.sonara.features.cadastrar.components.signupcard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sonara.core.ui.components.AppTextField
import com.example.sonara.domain.model.Gender
import com.example.sonara.domain.model.toDisplayName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderComboBox(
    selected: Gender?,
    onSelectedChange: (Gender) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            value = selected?.toDisplayName() ?: "",
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(text = "Gênero", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            modifier = Modifier.fillMaxWidth().menuAnchor() ,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                unfocusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            Gender.entries.forEach { gender ->

                DropdownMenuItem(
                    text = { Text(gender.toDisplayName()) },
                    onClick = {
                        onSelectedChange(gender)
                        expanded = false
                    }
                )
            }
        }
        if (isError && errorMessage != null) {

            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}