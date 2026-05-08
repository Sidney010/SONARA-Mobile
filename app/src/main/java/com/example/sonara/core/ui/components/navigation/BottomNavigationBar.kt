package com.example.sonara.core.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    state: BottomNavigationState,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {

    val items = listOf(
        BottomNavItemData.Home,
        BottomNavItemData.Search,
        BottomNavItemData.Events,
        BottomNavItemData.Plans
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(BottomNavDimens.NavigationHeight)
            .background(
                color = Color(0xffEA6012),
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