package com.example.sonara.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow // Certifique-se de que o import aponta para o layout padrão
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.features.home.components.EventHighlightCard
import com.example.sonara.features.home.components.FilterBottomSheet
import com.example.sonara.features.home.components.SmallEventCard
import com.example.sonara.features.home.viewmodel.HomeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ScreenContainer(
        verticalArrangement = Arrangement.SpaceBetween,
        verticalSpacing     = 6.dp,
        padding             = PaddingValues(12.dp, 40.dp)
    ) {
        // ── Header reativo ─────────────────────────────────────────────────────
        HomeHeader(
            state = HeaderUiState(
                userName  = uiState.userName,
                userRole  = uiState.userRole,
                avatarUrl = uiState.avatarUrl
            ),
            onLogoClick         = onNavigateToHome,
            onAvatarClick       = { if (uiState.isLoggedIn) onNavigateToProfile() else onNavigateToLogin() },
            onNotificationClick = {}
        )

        // ── Barra de busca + botão de filtro ───────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value         = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                // CORREÇÃO: Usando apenas o Modifier local encadeado corretamente
                modifier      = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp)),
                placeholder = {
                    Text("Pesquisar eventos...", color = Color.White, fontSize = 14.sp)
                },
                leadingIcon = {
                    IconButton(onClick = viewModel::onToggleFilterSheet) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filtros",
                            tint = if (uiState.filtroAtivo.ordinal > 0) Color(0xFFFF710C) else Color.White
                        )
                    }
                },
                trailingIcon = {
                    Icon(Icons.Default.Search, null, tint = Color.White)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor   = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor      = Color.White,
                    focusedTextColor        = Color.White,
                    cursorColor             = Color.White,
                    focusedIndicatorColor   = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true,
                shape = CircleShape
            )
        }

        // ── Título + badge de filtro ativo ────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Eventos próximos", color = Color.White, fontSize = 18.sp)
            if (uiState.filtroAtivo.ordinal > 0) {
                AssistChip(
                    onClick = { viewModel.onFiltroChange(com.example.sonara.features.home.model.EventoFiltro.PADRAO) },
                    label   = { Text(uiState.filtroAtivo.label, fontSize = 11.sp) }
                )
            }
        }

        // ── Lista de eventos ──────────────────────────────────────────────────
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFFF710C))
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(uiState.errorMessage!!, color = Color.Gray, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = viewModel::loadEventos) { Text("Tentar novamente") }
                    }
                }
            }

            uiState.eventosFiltrados.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text  = if (uiState.searchQuery.isNotBlank()) "Nenhum evento encontrado para \"${uiState.searchQuery}\"" else "Nenhum evento disponível no momento",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                val screenWidth    = LocalConfiguration.current.screenWidthDp.dp
                val smallCardWidth = (screenWidth - 24.dp - 16.dp) / 3  // 3 colunas

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .imePadding()
                        .padding(bottom = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Destaque (primeiro evento)
                    item {
                        EventHighlightCard(
                            evento      = uiState.eventosFiltrados.first(),
                            isLoggedIn  = uiState.isLoggedIn
                        )
                    }

                    // Banner anônimo — mostra dica se não logado
                    if (!uiState.isLoggedIn) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors   = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text  = "🔒  Faça login para interagir com os eventos",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(12.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Grade de eventos (restantes em 3 colunas)
                    val secondaryEvents = uiState.eventosFiltrados.drop(1)
                    if (secondaryEvents.isNotEmpty()) {
                        item {
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                maxItemsInEachRow = 3,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement   = Arrangement.spacedBy(8.dp)
                            ) {
                                secondaryEvents.forEach { evento ->
                                    SmallEventCard(
                                        evento     = evento,
                                        width      = smallCardWidth,
                                        isLoggedIn = uiState.isLoggedIn
                                    )
                                }
                            }
                        }
                    }

                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }

    // ── Filter Bottom Sheet ───────────────────────────────────────────────────
    if (uiState.showFilterSheet) {
        FilterBottomSheet(
            filtroAtivo       = uiState.filtroAtivo,
            onFiltroSelected  = viewModel::onFiltroChange,
            onDismiss         = viewModel::onDismissFilterSheet
        )
    }
}