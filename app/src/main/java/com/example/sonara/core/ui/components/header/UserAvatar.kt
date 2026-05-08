package com.example.sonara.core.ui.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun UserAvatar(
    avatarUrl: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .size(HeaderDimens.AvatarContainerSize)
            .clickable { onClick() },

        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(HeaderDimens.AvatarSizeFraction)
                .background(
                    color = Color.LightGray,
                    shape = CircleShape
                ),

            contentAlignment = Alignment.Center
        ) {

            if (avatarUrl == null) {

                AvatarPlaceholder()

            } else {

                // Aqui entra AsyncImage futuramente
            }
        }
    }
}