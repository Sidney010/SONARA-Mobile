package com.example.sonara.core.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AppGradients {

    val PrimaryBackground = Brush.verticalGradient(
        colorStops = arrayOf(
            0.36f to Color(0xFF000000),
            1.0f to Color(0xFFFC4C13)
        )
    )

    val PrimaryCardBackground = Brush.verticalGradient(
        colorStops = arrayOf(
            0f to Color(0x99FF0000),
            1.0f to Color(0xFFFC4C13)
        )
    )

    val SecondCardBackground = Brush.verticalGradient(
        colorStops = arrayOf(
            0.6f to Color(0x3CFF0000),
            1.0f to Color(0xFFFC4C13)
        )
    )

}