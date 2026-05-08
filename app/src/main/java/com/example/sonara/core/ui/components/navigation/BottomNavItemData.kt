package com.example.sonara.core.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

sealed class BottomNavItemData(
    val route: String,
    val title: String
) {

    data object Home : BottomNavItemData(
        route = "home",
        title = "Home"
    )

    data object Search : BottomNavItemData(
        route = "search",
        title = "Buscar"
    )

    data object Events : BottomNavItemData(
        route = "events",
        title = "Eventos"
    )

    data object Plans : BottomNavItemData(
        route = "plans",
        title = "Planos"
    )
}