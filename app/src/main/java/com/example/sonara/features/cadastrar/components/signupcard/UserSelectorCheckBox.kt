package com.example.sonara.features.cadastrar.components.signupcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sonara.domain.model.UserType
import com.example.sonara.domain.model.toDisplayName

@Composable
fun UserSelectorCheckBox(
    selected: Set<UserType>,
    onSelectedChange: (Set<UserType>) -> Unit,
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

                        Checkbox(
                            checked = option in selected,

                            onCheckedChange = { checked ->

                                val newSelection =
                                    if (checked) {
                                        selected + option
                                    } else {
                                        selected - option
                                    }

                                onSelectedChange(newSelection)
                            }
                        )

                        Text(
                            text = option.toDisplayName()
                        )
                    }
                }
            }
        }

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red
            )
        }
    }
}