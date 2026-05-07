package com.example.sonara.core.layout

import android.R.attr.verticalSpacing
import android.util.Log.i
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
import com.example.sonara.core.ui.theme.LocalGradients

@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(24.dp, 36.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    verticalSpacing: Dp = 50.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val gradients = LocalGradients.current

    // 1. Calculamos o arrangement antes de entrar no Column
    val finalArrangement = if (verticalSpacing == 0.dp) {
        verticalArrangement
    } else {
        Arrangement.spacedBy(verticalSpacing)
    }

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
            // 2. Passamos o valor já processado
            verticalArrangement = finalArrangement,
            content = content
        )
    }
}