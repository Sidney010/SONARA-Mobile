package com.example.sonara.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.runtime.staticCompositionLocalOf

val LocalGradients = staticCompositionLocalOf {
    DarkGradients
}

private val DarkColorScheme = darkColorScheme(
    primary = AppColors.PrimaryOrange,
    background = AppColors.BLack,
    surface = AppColors.BLack,
    onPrimary = AppColors.White,
    onBackground = AppColors.White
)

@Composable
fun SonaraTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = DarkColorScheme

    val gradients = DarkGradients

    CompositionLocalProvider(
        LocalGradients provides gradients
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            shapes = Shapes,
            content = content
        )
    }
}