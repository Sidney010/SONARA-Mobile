package com.example.sonara.features.login.components.logincard

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun LoginHeader(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = 20.sp
        ),
        color = MaterialTheme.colorScheme.onPrimary
    )
}
