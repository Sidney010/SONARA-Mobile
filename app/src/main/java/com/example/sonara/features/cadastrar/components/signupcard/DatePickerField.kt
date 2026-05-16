package com.example.sonara.features.cadastrar.components.signupcard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    value: String,            // formato "yyyy-MM-dd"
    onDateSelected: (String) -> Unit,
    isError: Boolean   = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    var showPicker by remember { mutableStateOf(false) }

    val displayText = remember(value) {
        if (value.isBlank()) "" else
            runCatching {
                LocalDate.parse(value).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            }.getOrElse { value }
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        OutlinedTextField(
            value       = displayText,
            onValueChange = {},
            readOnly    = true,
            placeholder = { Text("Data de nascimento", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
            trailingIcon = {
                IconButton(onClick = { showPicker = true }) {
                    Icon(Icons.Outlined.CalendarToday, contentDescription = "Abrir calendário")
                }
            },
            isError  = isError,
            modifier = Modifier.fillMaxWidth(),
            colors   = TextFieldDefaults.colors(
                focusedContainerColor     = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor   = MaterialTheme.colorScheme.surface,
                focusedTextColor          = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor        = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor     = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                unfocusedIndicatorColor   = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
            )
        )
        if (isError && errorMessage != null) {
            Text(
                text  = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }

    if (showPicker) {
        val datePickerState = rememberDatePickerState(
            // Limita data máxima a hoje (mínimo 18 anos: pode ajustar)
            initialSelectedDateMillis = if (value.isBlank()) null else
                runCatching {
                    LocalDate.parse(value)
                        .atStartOfDay(ZoneId.of("UTC"))
                        .toInstant()
                        .toEpochMilli()
                }.getOrNull()
        )

        DatePickerDialog(
            onDismissRequest  = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showPicker = false
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()
                        onDateSelected(date.format(DateTimeFormatter.ISO_DATE))
                    }
                }) { Text("Confirmar") }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

