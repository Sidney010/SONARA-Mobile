package com.example.sonara.features.artista.meusEventos.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.components.modal.ConfirmModal
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.domain.model.usuarioperfil.UsuarioEventoPerfil
import com.example.sonara.features.artista.meusEventos.components.EventoCardItem
import com.example.sonara.features.artista.meusEventos.viewmodel.MyEventsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyEvents(
    modifier: Modifier = Modifier,
    viewModel: MyEventsViewModel = hiltViewModel(),
    onEventClick: (Int, Int?) -> Unit = { _, _ -> }
) {
    val uiState by viewModel.uiState.collectAsState()
    var eventToDelete by remember { mutableStateOf<UsuarioEventoPerfil?>(null) }
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            uiState.userId?.let { viewModel.loadEventos(it, isRefreshing = true) }
        }
    }

    LaunchedEffect(uiState.isRefreshing) {
        if (!uiState.isRefreshing) {
            pullToRefreshState.endRefresh()
        }
    }

    if (eventToDelete != null) {
        ConfirmModal(
            title = "Cancelar Candidatura",
            message = "Deseja realmente cancelar sua candidatura para o evento ${eventToDelete?.eventoNome}?",
            confirmText = "Sim, cancelar",
            cancelText = "Não",
            onConfirm = {
                eventToDelete?.idEventoArtista?.let { viewModel.cancelCandidacy(it) }
                eventToDelete = null
            },
            onDismiss = { eventToDelete = null }
        )
    }

    ScreenContainer(
        modifier = modifier.nestedScroll(pullToRefreshState.nestedScrollConnection),
        verticalArrangement = Arrangement.Top,
        verticalSpacing = 16.dp,
        padding = PaddingValues(16.dp, 40.dp)
    ) {
        val headerState = HeaderUiState(
            userName = uiState.userName,
            userRole = uiState.userRole,
            avatarUrl = uiState.userPhoto,
            isLoggedIn = uiState.isLoggedIn
        )

        HomeHeader(
            state = headerState,
            onLogoClick = {},
            onAvatarClick = {},
            onNotificationClick = {}
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Meus Eventos",
                    color = AppColors.colorFontLogin,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                if (uiState.isLoading && !uiState.isRefreshing) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AppColors.colorFontLogin)
                    }
                } else if (uiState.eventos.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "Você ainda não se candidatou a nenhum evento.",
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(uiState.eventos) { evento ->
                            EventoCardItem(
                                evento = evento,
                                onClick = { onEventClick(evento.idEvento, evento.idEventoArtista) },
                                onDelete = { eventToDelete = evento }
                            )
                        }
                    }
                }
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = AppColors.colorFontLogin,
                containerColor = Color.Transparent
            )
        }
    }
}


