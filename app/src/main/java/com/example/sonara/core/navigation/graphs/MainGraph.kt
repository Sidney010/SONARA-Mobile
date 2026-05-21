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
import com.example.sonara.features.HomeCasaShow.HomeCreatedEventScreen
import com.example.sonara.features.criarEventoCasaDeShow.ui.CreateEventHouseShow
import com.example.sonara.features.home.ui.HomeScreen
//import com.example.sonara.features.meusEventos.ui.MyEventsScreen
import com.example.sonara.features.meusEventosCasaDeShow.ui.MyEventsHouseShowScreen
import com.example.sonara.features.perfilartista.ui.ArtistProfileScreen
import com.example.sonara.features.pesquisar.ui.SearchScreen
import com.example.sonara.features.plano.ui.PlansScreen
import com.example.sonara.features.sobreEvento.ui.AboutEventsScreen
import com.example.sonara.features.sobreEventoCasaDeShow.ui.AboutEventHouseShowScreenScreen


fun NavGraphBuilder.mainGraph(
    rootNavController: NavController
) {
    // ── Shell principal com BottomNav ──────────────────────────────────
    composable(Routes.Main.route) {
        val bottomNavController = rememberNavController()
        val navBackStackEntry   by bottomNavController.currentBackStackEntryAsState()
        val currentRoute        = navBackStackEntry?.destination?.route

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
                        onNavigateToHome = {
                            bottomNavController.navigate(Routes.Home.route) {
                                launchSingleTop = true
                                popUpTo(bottomNavController.graph.startDestinationId) {
                                    inclusive = false
                                }
                            }
                        },
                        onNavigateToProfile = {
                            rootNavController.navigate(Routes.Profile.route)
                        },
                        onNavigateToLogin = {
                            rootNavController.navigate(Routes.Login.route)
                        }
                    )
                }

                composable(Routes.Search.route) {
                     SearchScreen()
                }

                composable(Routes.Events.route) {
//                     EventsScreen()
//                    HomeCreatedEventScreen()
//                    MyEventsHouseShowScreen()
//                    CreateEventHouseShow()
//                    AboutEventsScreen()
//                    AboutEventHouseShowScreenScreen()


                }

                composable(Routes.Plans.route) {
                    PlansScreen(
                        onNavigateToHome    = {
                            bottomNavController.navigate(Routes.Home.route) {
                                launchSingleTop = true
                            }
                        },
                        onNavigateToProfile = {
                            rootNavController.navigate(Routes.Profile.route)
                        },
                        onNavigateToLogin   = {
                            rootNavController.navigate(Routes.Login.route)
                        }
                    )
                }
            }
        }
    }

    // ── Perfil (fora do BottomNav) ────────────────────────────────────
    composable(Routes.Profile.route) {
        ArtistProfileScreen(
            onNavigateBack    = { rootNavController.popBackStack() },
            onNavigateToStart = {
                // Volta para a tela inicial removendo todo o back-stack
                rootNavController.navigate(Routes.Start.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }
}