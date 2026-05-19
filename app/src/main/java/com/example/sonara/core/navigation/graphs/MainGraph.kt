package com.example.sonara.core.navigation.graphs

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sonara.core.navigation.Routes
import com.example.sonara.core.ui.components.navigation.BottomNavigationBar
import com.example.sonara.core.ui.components.navigation.BottomNavigationState
import com.example.sonara.features.perfilartista.ui.ArtistProfileScreen
import com.example.sonara.features.home.ui.HomeScreen
import com.example.sonara.features.plano.ui.PlansScreen

fun NavGraphBuilder.mainGraph(
    rootNavController: NavController
) {

    // ── Main Shell (contém o BottomNav + tabs) ────────────────────────────────
    composable(Routes.Main.route) {

        val bottomNavController = rememberNavController()

        val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    state     = BottomNavigationState(selectedRoute = currentRoute ?: ""),
                    onNavigate = { route ->
                        bottomNavController.navigate(route) {
                            launchSingleTop = true
                            restoreState    = true
                            popUpTo(bottomNavController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                )
            }
        ) { _ ->

            NavHost(
                navController    = bottomNavController,
                startDestination = Routes.Home.route
            ) {

                composable(Routes.Home.route) {
                    HomeScreen(
                        // Logo clicado → volta para a aba Home
                        onNavigateToHome = {
                            bottomNavController.navigate(Routes.Home.route) {
                                launchSingleTop = true
                                popUpTo(bottomNavController.graph.startDestinationId) {
                                    inclusive = false
                                }
                            }
                        },
                        // Avatar clicado e usuário ESTÁ logado → Perfil
                        onNavigateToProfile = {
                            rootNavController.navigate(Routes.Profile.route)
                        },
                        // Avatar clicado e usuário NÃO está logado → Login
                        onNavigateToLogin = {
                            rootNavController.navigate(Routes.Login.route)
                        }
                    )
                }

                composable(Routes.Search.route) {
                    // SearchScreen()
                }

                composable(Routes.Events.route) {
                    // EventsScreen()
                }

                composable(Routes.Plans.route) {
                    PlansScreen()
                }
            }
        }
    }

    // ── Perfil do usuário (fora do BottomNav) ─────────────────────────────────
    // Rota alcançada pelo rootNavController, por isso não aparece no BottomNav
    composable(Routes.Profile.route) {
        ArtistProfileScreen(
            onNavigateBack = { rootNavController.popBackStack() }
        )
    }
}