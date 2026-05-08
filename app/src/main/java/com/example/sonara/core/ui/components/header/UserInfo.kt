package com.example.sonara.core.ui.components.header

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun UserInfo(
    userName: String,
    userRole: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxHeight()
            .wrapContentHeight(),

        horizontalAlignment = Alignment.End
    ) {

        Text(
            text = userName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End
        )

        Text(
            text = userRole,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.End
        )
    }
}