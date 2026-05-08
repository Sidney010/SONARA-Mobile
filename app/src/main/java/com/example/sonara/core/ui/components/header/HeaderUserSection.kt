package com.example.sonara.core.ui.components.header

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HeaderUserSection(
    state: HeaderUiState,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit,
    onNotificationClick: () -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            HeaderDimens.ItemSpacing
        )
    ) {

        HeaderActions(
            onNotificationClick = onNotificationClick
        )

        UserInfo(
            userName = state.userName,
            userRole = state.userRole
        )

        UserAvatar(
            avatarUrl = state.avatarUrl,
            onClick = onAvatarClick
        )

    }
}