package com.example.sonara.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class AppGradientColors(
    val primaryBackground: Brush,
    val primaryCard: Brush,
    val secondaryCard: Brush,
    val thirdCard: Brush
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
    ),
    thirdCard = Brush.verticalGradient(
        colorStops = arrayOf(
            0.6f to Color(0x40FF0000),
            0.6f to Color(0x46A62525),
        )
    )
)

internal val LightGradients = AppGradientColors(
    primaryBackground = Brush.verticalGradient(
        colorStops = arrayOf(
            0.36f to Color(0xFF000000),
            1.0f  to Color(0xFF464646)
        )
    ),
    primaryCard = Brush.verticalGradient(
        colorStops = arrayOf(
            0f    to Color(0x99121212),
            1.0f  to Color(0xFF464646)
        )
    ),
    secondaryCard = Brush.verticalGradient(
        colorStops = arrayOf(
            0.6f  to Color(0x3C121212),
            1.0f  to Color(0xFF464646)
        )


    ),
    thirdCard = Brush.verticalGradient(
        colorStops = arrayOf(
            0.6f  to Color(0x40121212),
            1.0f  to Color(0x46464646)
        )


    )
)
