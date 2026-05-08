package com.example.sonara.core.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.sonara.core.animation.NavigationAnimations
import com.example.sonara.core.navigation.Routes
import com.example.sonara.features.cadastrar.ui.SignUpScreen
import com.example.sonara.features.inicial.ui.StartWelcomeScreen
import com.example.sonara.features.login.ui.LoginScreen
import com.example.sonara.features.recuperarsenha.ui.RecoverPasswordScreen
import com.example.sonara.features.trocarrsenha.ui.RedefinedPasswordScreen

fun NavGraphBuilder.authGraph(
    navController: NavController
) {

    composable(Routes.Start.route) {

        StartWelcomeScreen(

            onNavigateToLogin = {
                navController.navigate(Routes.Login.route)
            },

            onNavigateAnonymous = {

                navController.navigate(Routes.Main.route) {

                    popUpTo(Routes.Start.route) {
                        inclusive = true
                    }
                }
            }
        )
    }

    composable(
        route = Routes.Login.route,
        enterTransition = { NavigationAnimations.enter() },
        exitTransition = { NavigationAnimations.exit() },
        popEnterTransition = { NavigationAnimations.popEnter() },
        popExitTransition = { NavigationAnimations.popExit() }
    ) {

        LoginScreen(

            onNavigateToRecoverPassword = {
                navController.navigate(
                    Routes.RecoverPassword.route
                )
            },

            onNavigateSignUp = {
                navController.navigate(
                    Routes.SignUp.route
                )
            },

//            onLoginSuccess = {
//
//                navController.navigate(Routes.Main.route) {
//
//                    popUpTo(Routes.Login.route) {
//                        inclusive = true
//                    }
//                }
//            }
        )
    }

    composable(Routes.SignUp.route) {

        SignUpScreen(

            onNavigateToLogin = {
                navController.popBackStack()
            },

//            onSignUpSuccess = {
//
//                navController.navigate(Routes.Main.route)
//            }
        )
    }

    composable(Routes.RecoverPassword.route) {

        RecoverPasswordScreen(

            onNavigateToRedefinedPassword = {
                navController.navigate(
                    Routes.RedefinedPassword.route
                )
            }
        )
    }

    composable(Routes.RedefinedPassword.route) {

        RedefinedPasswordScreen(

            onNavigateToLogin = {

                navController.navigate(
                    Routes.Login.route
                ) {

                    popUpTo(Routes.Login.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}