package com.example.sonara.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalGradients = staticCompositionLocalOf {
    DarkGradients
    LightGradients
}

private val DarkColorScheme = darkColorScheme(
    primary = AppColors.PrimaryOrange,
    background = AppColors.BLack,
    surface = AppColors.BLack,
    onPrimary = AppColors.White,
    onBackground = AppColors.White,
    tertiary = AppColors.colorOrangeDark,
    outline = AppColors.colorOrangeMoreDark
)

private val LightColorScheme = darkColorScheme(
    primary        = AppColors.PrimaryColor,
    background     = AppColors.Background,
    surface        = AppColors.BLack,
    onPrimary      = AppColors.White,
    onBackground   = AppColors.White,
    tertiary       = AppColors.SecondColor,
    outline        = AppColors.colorThirt
)


@Composable
fun SonaraTheme(
    darkTheme: Boolean = true,
    lightTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = LightColorScheme

    val gradients = LightGradients

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