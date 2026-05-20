package com.example.sonara.core.ui.components.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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