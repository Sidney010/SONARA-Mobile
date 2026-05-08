package com.example.sonara.core.ui.components.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavLabel(
    title: String
) {

    Text(
        text = title,
        color = Color.White
    )
}