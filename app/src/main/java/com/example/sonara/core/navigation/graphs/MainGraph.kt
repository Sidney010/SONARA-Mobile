package com.example.sonara.core.navigation.graphs

import SeeArtistsHouseShowScreen
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sonara.core.navigation.Routes
import com.example.sonara.core.ui.components.navigation.BottomNavigationBar
import com.example.sonara.core.ui.components.navigation.BottomNavigationState
import com.example.sonara.features.home.ui.HomeScreen
//import com.example.sonara.features.meusEventos.ui.MyEventsScreen
import com.example.sonara.features.artista.perfilartista.ui.ArtistProfileScreen
import com.example.sonara.features.artista.sobreEvento.ui.AboutEventsScreen
import com.example.sonara.features.organizador.criareventoorganizador.ui.CreateEventOrganizerScreen
import com.example.sonara.features.organizador.homeorganizador.ui.HomeOrganizerScreen
import com.example.sonara.features.organizador.meuseventosorganizador.ui.MyEventsOrganizerScreen
import com.example.sonara.features.organizador.pesquisarartistacasadeshow.ui.SearchArtistHouseshowScreen
import com.example.sonara.features.organizador.selectartist.ui.SelectArtistScreen
import com.example.sonara.features.organizador.sobreeventoorganizador.ui.AboutEventOrganizerScreen
import com.example.sonara.features.organizador.telaperfilorganizador.ui.OrganizerProfileScreen
import com.example.sonara.features.pesquisar.ui.SearchScreen
import com.example.sonara.features.plano.ui.PlansScreen
import com.example.sonara.features.usuario.sobreoeventoselecionado.ui.AboutSelectionEventScreen
import com.example.sonara.features.usuario.telahomeusuario.HomeUserScreen
import com.example.sonara.features.usuario.telaperfilusuario.ui.ProfileUserScreen
import com.example.sonara.features.usuario.telausuariopesquisar.ui.SearchEventsUserScreen


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
//                    HomeOrganizerScreen()
//                    MyEventsOrganizerScreen()
//                      CreateEventOrganizerScreen()
//                    AboutEventsScreen()
//                    AboutEventOrganizerScreen()
//                    OrganizerProfileScreen()
//                    SelectArtistScreen()
//                    SeeArtistsHouseShowScreen()
//                    HomeUserScreen() {}
//                    SearchEventsUserScreen()
//                    AboutSelectionEventScreen()
//                      ProfileUserScreen()
//                    SearchArtistHouseshowScreen()


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
                    Spacer(modifier = Modifier.height(80.dp))
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