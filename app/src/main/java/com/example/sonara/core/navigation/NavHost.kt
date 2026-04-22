package com.example.sonara.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sonara.core.animation.NavigationAnimations
import com.example.sonara.features.cadastrar.ui.SignUpScreen
import com.example.sonara.features.inicial.ui.StartWelcomeScreen
import com.example.sonara.features.login.ui.LoginScreen
import com.example.sonara.features.recuperarsenha.ui.RecoverPasswordScreen
import com.example.sonara.features.trocarrsenha.components.RedefinedPasswordCard
import com.example.sonara.features.trocarrsenha.ui.RedefinedPasswordScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.START
    ) {

        composable(Routes.START) {
            StartWelcomeScreen(
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
            LoginScreen(
                onNavigateToRecoverPassword = {
                    navController.navigate(Routes.RECOVER_PASSWORD)
                },
                onNavigateSignUp = {
                    navController.navigate(Routes.SIGN_UP)
                }
            )
        }

        composable(
            route = Routes.RECOVER_PASSWORD,
            enterTransition = { NavigationAnimations.enter() },
            exitTransition = { NavigationAnimations.exit() },
            popEnterTransition = { NavigationAnimations.popEnter() },
            popExitTransition = { NavigationAnimations.popExit() }
        ) {
            RecoverPasswordScreen(
                onNavigateToRedefinedPassword = {
                    navController.navigate(Routes.REDEFINED_PASSWORD)
                }
            )
        }
        composable(
            route = Routes.REDEFINED_PASSWORD,
            enterTransition = { NavigationAnimations.enter() },
            exitTransition = { NavigationAnimations.exit() },
            popEnterTransition = { NavigationAnimations.popEnter() },
            popExitTransition = { NavigationAnimations.popExit() }
        ) {
            RedefinedPasswordScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }
        composable(
            route = Routes.SIGN_UP,
            enterTransition = { NavigationAnimations.enter() },
            exitTransition = { NavigationAnimations.exit() },
            popEnterTransition = { NavigationAnimations.popEnter() },
            popExitTransition = { NavigationAnimations.popExit() }
        ){
            SignUpScreen()
        }
    }
}