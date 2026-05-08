package com.example.sonara.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.sonara.core.navigation.graphs.authGraph
import com.example.sonara.core.navigation.graphs.mainGraph

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Start.route
    ) {

        authGraph(navController)

        mainGraph(navController)
    }
}