package com.example.sonara.features.organizador.meuseventosorganizador.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.DarkGradients
import com.example.sonara.domain.model.Evento
import com.example.sonara.features.home.components.formatarData
import com.example.sonara.features.home.components.formatarHora
import com.example.sonara.features.organizador.meuseventosorganizador.viewmodel.MyEventsOrganizerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyEventsOrganizerScreen(
    modifier: Modifier = Modifier,
    viewModel: MyEventsOrganizerViewModel = hiltViewModel(),
    onEventClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val gradients = DarkGradients
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            uiState.userId?.let { viewModel.loadEventosDoPerfil(it, isRefreshing = true) }
        }
    }

    LaunchedEffect(uiState.isRefreshing) {
        if (!uiState.isRefreshing) {
            pullToRefreshState.endRefresh()
        }
    }

    ScreenContainer(
        modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection),
        verticalArrangement = Arrangement.Top,
        verticalSpacing = 20.dp,
        padding = PaddingValues(12.dp, 40.dp)
    ) {
        HomeHeader(
            state = HeaderUiState(
                userName = uiState.userName,
                userRole = uiState.userRole,
                avatarUrl = uiState.userPhoto,
                isLoggedIn = uiState.isLoggedIn
            ),
            onLogoClick = {},
            onAvatarClick = {},
            onNotificationClick = {}
        )

        Text(
            text ="Meus Eventos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center

        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading && !uiState.isRefreshing) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFF710C))
                }
            } else if (uiState.errorMessage != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(uiState.errorMessage!!, color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.eventos) { evento ->
                        EventoCardItem(
                            evento = evento,
                            onClick = { onEventClick(evento.id) }
                        )
                    }
                }
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = Color(0xFFFF710C),
                containerColor = Color.Transparent
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun EventoCardItem(
    evento: Evento,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradients = DarkGradients

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .background(brush = gradients.secondaryCard)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = evento.fotosUrls.firstOrNull(),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = evento.nome,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "${formatarData(evento.data)}   ${formatarHora(evento.horaInicio)}",
                    fontSize = 12.sp,
                    color = Color(0xFFFFAA70)
                )
                Text(
                    text = evento.descricao ?: "Sem descrição",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    maxLines = 2
                )
            }
        }
    }
}