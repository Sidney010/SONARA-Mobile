package com.example.sonara.core.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavIcon(
    item: BottomNavItemData,
    isSelected: Boolean
) {

    val icon = when(item) {
        BottomNavItemData.Home -> Icons.Default.Home
        BottomNavItemData.Search -> Icons.Default.Search
        BottomNavItemData.Events -> Icons.Default.CheckCircle
        BottomNavItemData.Plans -> Icons.Default.Build
    }

    Box(
        modifier = Modifier
            .fillMaxSize(0.7f)
            .background(
                color = if (isSelected)
                    Color.White
                else
                    Color.LightGray,

                shape = CircleShape
            ),

        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = icon,
            contentDescription = item.title,
            tint = if (isSelected)
                Color(0xffEA6012)
            else
                Color.White
        )
    }
}