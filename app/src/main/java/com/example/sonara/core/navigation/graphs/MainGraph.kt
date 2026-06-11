package com.example.sonara.core.navigation.graphs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.sonara.features.home.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
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
import com.example.sonara.features.artista.perfilartista.ui.ArtistProfileScreen
import com.example.sonara.features.artista.sobreEvento.ui.AboutEventsScreen
import com.example.sonara.features.organizador.criareventoorganizador.ui.CreateEventOrganizerScreen
import com.example.sonara.features.organizador.verartistatelacasashow.ui.SeeArtistsHouseShowScreen
import com.example.sonara.features.organizador.meuseventosorganizador.ui.MyEventsOrganizerScreen
import com.example.sonara.features.organizador.selectartist.ui.SelectArtistScreen
import com.example.sonara.features.organizador.sobreeventoorganizador.ui.AboutEventOrganizerScreen
import com.example.sonara.features.plano.ui.PlansScreen
import com.example.sonara.features.usuario.sobreoeventoselecionado.ui.AboutSelectionEventScreen


import com.example.sonara.features.artista.candidaturaStatus.ui.YourCandidacyScreen
import com.example.sonara.features.organizador.homeorganizador.ui.HomeOrganizerScreen

fun NavGraphBuilder.mainGraph(
    rootNavController: NavController
) {
    // ── Shell principal com BottomNav ──────────────────────────────────
    composable(Routes.Main.route) {
        val bottomNavController = rememberNavController()
        val navBackStackEntry   by bottomNavController.currentBackStackEntryAsState()
        val currentRoute        = navBackStackEntry?.destination?.route
        val viewModel: HomeViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    state     = BottomNavigationState(
                        selectedRoute = currentRoute ?: "",
                        userRole = uiState.userRole
                    ),
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
        ) { paddingValues ->
            NavHost(
                navController    = bottomNavController,
                startDestination = Routes.Home.route,
            ) {
                composable(Routes.Home.route) {
                    val uiState by viewModel.uiState.collectAsState()

                    if (uiState.userRole == "Organizador" || uiState.userRole == "ORGANIZADOR") {
                        HomeOrganizerScreen(
                            onNavigateToCreateEvent = { bottomNavController.navigate(Routes.CreateEvent.route) },
                            onNavigateToHireArtist = { bottomNavController.navigate(Routes.HireArtist.route) },
                            onNavigateToMyEvents = { bottomNavController.navigate(Routes.MyEvents.route) },
                            onNavigateToProfile = { rootNavController.navigate(Routes.Profile.route) }
                        )
                    } else {
                        HomeScreen(
                            onNavigateToEventDetails = { eventId ->
                                val route = if (uiState.userRole == "Artista" || uiState.userRole == "ARTISTA" ) {
                                    "about_event_artist/$eventId"
                                } else {
                                    "about_event_user/$eventId"
                                }
                                bottomNavController.navigate(route)
                            },
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
                }

                // ── Detalhes do Evento por Perfil ──────────────────────────────

                composable("about_event_artist/{eventId}") { backStackEntry ->
                    val eventId = backStackEntry.arguments?.getString("eventId")?.toInt() ?: 0
                     AboutEventsScreen(
                        onNavigateToProfile = { rootNavController.navigate(Routes.Profile.route) },
                        onNavigateToLogin = { rootNavController.navigate(Routes.Login.route) },
                        eventId = eventId,
                        onBackClick = { bottomNavController.popBackStack() },
                        onApplyClick = { id, eaId ->
                            val route = if (eaId != null) "your_candidacy/$id?eaId=$eaId" else "your_candidacy/$id"
                            bottomNavController.navigate(route)
                        }
                    )
                }

                composable("your_candidacy/{eventId}?eaId={eaId}") { backStackEntry ->
                    val eventId = backStackEntry.arguments?.getString("eventId")?.toInt() ?: 0
                    val eaId = backStackEntry.arguments?.getString("eaId")?.toIntOrNull()
                    YourCandidacyScreen(
                        onNavigateToProfile = { rootNavController.navigate(Routes.Profile.route) },
                        onNavigateToLogin = { rootNavController.navigate(Routes.Login.route) },
                        eventoId = eventId,
                        eventoArtistaId = eaId,
                        onBack = { bottomNavController.popBackStack() }
                    )
                }

                composable("about_event_user/{eventId}") { backStackEntry ->
                    val eventId = backStackEntry.arguments?.getString("eventId")?.toInt() ?: 0
                    AboutSelectionEventScreen(
                        onNavigateToProfile = {rootNavController.navigate(Routes.Profile.route) },
                        onNavigateToLogin = {rootNavController.navigate(Routes.Login.route) },
                        eventId = eventId,
                        // Assumindo que esta tela recebe o eventId
                        // Se a assinatura for diferente, ajuste conforme necessário
                        onBackClick = { bottomNavController.popBackStack() }
                    )
                }

                composable("about_event_organizer/{eventId}") { backStackEntry ->
                    val eventId = backStackEntry.arguments?.getString("eventId")?.toInt() ?: 0
                    AboutEventOrganizerScreen(
                        onNavigateToProfile = {rootNavController.navigate(Routes.Profile.route) },
                        onNavigateToLogin = {rootNavController.navigate(Routes.Login.route) },
                        eventId = eventId,
                        onBackClick = { bottomNavController.popBackStack() }
                    )
                }

                composable(Routes.Search.route) {
                    val viewModel: HomeViewModel = hiltViewModel()
                    val uiState by viewModel.uiState.collectAsState()

                    if (uiState.userRole == "ORGANIZADOR" || uiState.userRole == "Organizador") {
                        SeeArtistsHouseShowScreen(
                            onNavigateToProfile = { rootNavController.navigate(Routes.Profile.route) },
                            onNavigateToArtistDetails = { artistId ->
                                // TODO: Navigate to artist details when available
                                // bottomNavController.navigate("artist_details/$artistId")
                            }
                        )
                    } else {
                        com.example.sonara.features.pesquisar.ui.SearchScreen()
                    }
                }

                composable(Routes.Events.route) {
                    val viewModel: HomeViewModel = hiltViewModel()
                    val uiState by viewModel.uiState.collectAsState()

                    if (uiState.userRole == "ORGANIZADOR" || uiState.userRole == "Organizador") {
                        MyEventsOrganizerScreen(
                            onEventClick = { eventId ->
                                bottomNavController.navigate("about_event_organizer/$eventId")
                            },
                            onBackClick = { bottomNavController.popBackStack() }
                        )
                    } else if (uiState.userRole == "ARTISTA" || uiState.userRole == "Artista") {
                        com.example.sonara.features.artista.meusEventos.ui.MyEvents(
                            onEventClick = { eventId, eaId ->
                                bottomNavController.navigate("your_candidacy/$eventId?eaId=$eaId")
                            }
                        )
                    } else {
                        // MyEventsScreen() // Para usuários comuns se necessário
                    }
                }

                composable(Routes.CreateEvent.route) {
                    CreateEventOrganizerScreen(
                        onBackClick = { bottomNavController.popBackStack() }
                    )
                }

                composable(Routes.HireArtist.route) {
                    SelectArtistScreen(
                        onBackClick = { bottomNavController.popBackStack() }
                    )
                }

                composable(Routes.MyEvents.route) {
                    MyEventsOrganizerScreen(
                        onEventClick = { eventId ->
                            bottomNavController.navigate("about_event_organizer/$eventId")
                        },
                        onBackClick = { bottomNavController.popBackStack() }
                    )
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
        val homeViewModel: HomeViewModel = hiltViewModel()
        val homeUiState by homeViewModel.uiState.collectAsState()

        // Decidir qual tela de perfil mostrar baseado no cargo do usuário
        val userRole = homeUiState.userRole?.uppercase()
        
        if (userRole == "USUARIO" || userRole == "USER") {
             // Por enquanto redireciona ou usa ArtistProfileScreen que já é genérica o suficiente 
             // mas vamos tentar usar a ArtistProfileScreen que já lida com UsuarioPerfil genérico
             ArtistProfileScreen(
                onNavigateBack    = { rootNavController.popBackStack() },
                onNavigateToStart = {
                    rootNavController.navigate(Routes.Start.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        } else {
            ArtistProfileScreen(
                onNavigateBack    = { rootNavController.popBackStack() },
                onNavigateToStart = {
                    rootNavController.navigate(Routes.Start.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}