package com.example.sonara.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun AppCardHeader(
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
