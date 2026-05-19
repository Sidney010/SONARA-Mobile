package com.example.sonara.core.navigation.graphs

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.example.sonara.features.cadidatar.ui.ApplyScreen
import com.example.sonara.features.home.ui.HomeScreen
import com.example.sonara.features.perfilArtista.ui.ArtistProfileScreen
import com.example.sonara.features.plano.ui.PlansScreen

fun NavGraphBuilder.mainGraph(
    rootNavController: NavController
) {

    composable(Routes.Main.route) {

        val bottomNavController = rememberNavController()

        val navBackStackEntry by bottomNavController
            .currentBackStackEntryAsState()

        val currentRoute =
            navBackStackEntry?.destination?.route

        Scaffold(

            bottomBar = {

                BottomNavigationBar(

                    state = BottomNavigationState(
                        selectedRoute = currentRoute ?: ""
                    ),

                    onNavigate = { route ->

                        bottomNavController.navigate(route) {

                            launchSingleTop = true

                            restoreState = true

                            popUpTo(
                                bottomNavController
                                    .graph
                                    .startDestinationId
                            ) {

                                saveState = true
                            }
                        }
                    }
                )
            }

        ) { paddingValues ->

            NavHost(
                navController = bottomNavController,
                startDestination = Routes.Home.route
            ) {

                composable(Routes.Home.route) {
                    HomeScreen(

                    )
                }

                composable(Routes.Search.route) {
                    // SearchScreen()
                    ArtistProfileScreen()
                }

                composable(Routes.Events.route) {
                    // EventsScreen()
                    ApplyScreen()
                }

                composable(Routes.Plans.route) {
                    PlansScreen()
                }
            }
        }
    }
}