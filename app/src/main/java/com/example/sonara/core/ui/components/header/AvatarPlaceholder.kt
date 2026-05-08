package com.example.sonara.core.ui.components.header

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AvatarPlaceholder() {

    Icon(
        imageVector = Icons.Default.Person,
        contentDescription = "Avatar Placeholder",
        tint = Color.White
    )
}