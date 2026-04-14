package com.example.sonara.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sonara.core.animation.NavigationAnimations
import com.example.sonara.features.inicial.ui.StartBemVindoScreen
import com.example.sonara.features.login.ui.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.START
    ) {

        composable(Routes.START) {
            StartBemVindoScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }

        composable(
            route = Routes.LOGIN,
            enterTransition = { NavigationAnimations.enter() },
            exitTransition = { NavigationAnimations.exit() },
            popEnterTransition = { NavigationAnimations.popEnter() },
            popExitTransition = { NavigationAnimations.popExit() }
        ) {
            LoginScreen()
        }
    }
}