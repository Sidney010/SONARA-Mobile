package com.example.sonara.core.ui.components.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BottomNavItem(
    item: BottomNavItemData,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .size(BottomNavDimens.ItemSize)
            .clickable { onClick() },

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BottomNavIcon(
            item = item,
            isSelected = isSelected
        )

        BottomNavLabel(
            title = item.title
        )
    }
}