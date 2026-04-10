package com.example.sonara.features.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.sonara.core.theme.AppGradients

@Composable
fun StartBemVindoScreen(modifier: Modifier = Modifier) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Blue, Color.Cyan)
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppGradients.PrimaryBackground)

    ){
        Column() { }
    }

}

