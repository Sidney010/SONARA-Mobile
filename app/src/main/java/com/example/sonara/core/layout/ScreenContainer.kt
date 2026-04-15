package com.example.sonara.core.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.sonara.core.theme.LocalGradients

@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(24.dp, 36.dp),
    verticalSpacing: Dp = 50.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val gradients = LocalGradients.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradients.primaryBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(verticalSpacing),
            content = content
        )
    }
}