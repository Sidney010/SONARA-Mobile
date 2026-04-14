package com.example.sonara.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.sonara.R

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