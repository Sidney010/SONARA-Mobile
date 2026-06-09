package com.example.sonara.core.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sonara.core.ui.theme.AppColors

@Composable
fun BottomNavigationBar(
    state: BottomNavigationState,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {

    val items = when (state.userRole.lowercase()) {
        "artista" -> listOf(
            BottomNavItemData.Home,
            BottomNavItemData.Events,
            BottomNavItemData.Plans
        )
        "organizador" -> listOf(
            BottomNavItemData.Home,
            BottomNavItemData.Search,
            BottomNavItemData.Events,
            BottomNavItemData.Plans
        )
        else -> listOf(
            BottomNavItemData.Home
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(BottomNavDimens.NavigationHeight)
            .background(
                color = AppColors.SecondColor,
                shape = RoundedCornerShape(
                    BottomNavDimens.CornerRadius
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        items.forEach { item ->

            BottomNavItem(
                item = item,
                isSelected = state.selectedRoute == item.route,
                onClick = {
                    onNavigate(item.route)
                }
            )
        }
    }
}