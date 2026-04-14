package com.example.sonara.core.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class AppGradientColors(
    val primaryBackground: Brush,
    val primaryCard: Brush,
    val secondaryCard: Brush
)

internal val DarkGradients = AppGradientColors(
    primaryBackground = Brush.verticalGradient(
        colorStops = arrayOf(
            0.36f to Color(0xFF000000),
            1.0f to Color(0xFFFC4C13)
        )
    ),
    primaryCard = Brush.verticalGradient(
        colorStops = arrayOf(
            0f to Color(0x99FF0000),
            1.0f to Color(0xFFFC4C13)
        )
    ),
    secondaryCard = Brush.verticalGradient(
        colorStops = arrayOf(
            0.6f to Color(0x3CFF0000),
            1.0f to Color(0xFFFC4C13)
        )
    )
)

internal val LightGradients = AppGradientColors(
    primaryBackground = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to Color(0xFFFFFFFF),
            1.0f to Color(0xFFFFE0D6)
        )
    ),
    primaryCard = Brush.verticalGradient(
        colorStops = arrayOf(
            0f to Color(0x33FC4C13),
            1.0f to Color(0xFFFFA07A)
        )
    ),
    secondaryCard = Brush.verticalGradient(
        colorStops = arrayOf(
            0.6f to Color(0x1AFC4C13),
            1.0f to Color(0xFFFFC1A6)
        )
    )
)