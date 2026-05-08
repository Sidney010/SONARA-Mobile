package com.example.sonara.core.ui.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeHeader(
    state: HeaderUiState,
    modifier: Modifier = Modifier,
    onLogoClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(HeaderDimens.HeaderHeight),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        HeaderLogo(
            onClick = onLogoClick
        )

        HeaderUserSection(
            state = state,
            onAvatarClick = onAvatarClick,
            onNotificationClick = onNotificationClick
        )
    }
}