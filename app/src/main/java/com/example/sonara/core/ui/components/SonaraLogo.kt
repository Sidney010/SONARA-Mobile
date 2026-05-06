package com.example.sonara.core.ui.components

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.example.sonara.R
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON

@Composable
fun SonaraLogo(
    modifier: Modifier = Modifier,
    size: Dp? = null
) {
    val finalModifier = if (size != null) {
        modifier.size(size)
    } else {
        modifier
    }


    Image(
        painter = painterResource(R.drawable.sonara_logo),
        contentDescription = "Logo da Sonara",
        modifier = finalModifier
    )
}

@Composable
fun SonaraLogoSVG(
    modifier: Modifier = Modifier,
    size: Dp? = null
) {
    val finalModifier = if (size != null) {
        modifier.size(size)
    } else {
        modifier
    }

    Image(
        painterResource(R.drawable.sonara_logo_svg),
        contentDescription = "Logo da Sonara",
        modifier = finalModifier
    )
}