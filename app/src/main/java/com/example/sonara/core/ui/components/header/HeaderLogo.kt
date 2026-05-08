package com.example.sonara.core.ui.components.header

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sonara.core.ui.components.SonaraLogoSVG

@Composable
fun HeaderLogo(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    SonaraLogoSVG(
        modifier = modifier
            .fillMaxHeight(HeaderDimens.LogoHeightFraction)
            .clickable { onClick() }
    )
}