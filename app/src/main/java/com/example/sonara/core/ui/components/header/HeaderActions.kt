package com.example.sonara.core.ui.components.header

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun HeaderActions(
    onNotificationClick: () -> Unit
) {

    IconButton(
        onClick = onNotificationClick
    ) {

        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notificações",
            tint = Color.White
        )
    }
}