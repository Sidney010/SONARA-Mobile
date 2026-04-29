package com.example.sonara.features.cadastrar.components.signupcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sonara.domain.model.UserType
import com.example.sonara.domain.model.toDisplayName

@Composable
fun UserSelectorRadioButton(
    selected: UserType?,
    onSelectedChange: (UserType) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    val options = UserType.entries

    Column {
        options.chunked(2).forEach { rowOptions ->
            Row {
                rowOptions.forEach { option ->
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected == option,
                            onClick = { onSelectedChange(option) }
                        )
                        Text(text = option.toDisplayName())
                    }
                }

//                // Se for número ímpar, completa o espaço
//                if (rowOptions.size == 1) {
//                    Spacer(modifier = Modifier.weight(1f))
//                }
            }
        }

        // erro (se houver)
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red
            )
        }
    }
}
