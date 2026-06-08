package com.example.sonara.features.artista.meusEventos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.core.ui.theme.DarkGradients
import com.example.sonara.domain.model.usuarioperfil.UsuarioEventoPerfil
import com.example.sonara.features.artista.meusEventos.viewmodel.MyEventsViewModel
import com.example.sonara.features.home.components.formatarData
import com.example.sonara.features.home.components.formatarHora

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.example.sonara.core.ui.components.modal.ConfirmModal

@Composable
fun MyEvents(
    modifier: Modifier = Modifier,
    viewModel: MyEventsViewModel = hiltViewModel(),
    onEventClick: (Int, Int?) -> Unit = { _, _ -> }
) {
    val uiState by viewModel.uiState.collectAsState()
    var eventToDelete by remember { mutableStateOf<UsuarioEventoPerfil?>(null) }

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
        modifier = modifier,
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "Meus Eventos",
                color = AppColors.colorFontLogin,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            if (uiState.isLoading) {
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
    }
}

@Composable
fun EventoCardItem(
    evento: UsuarioEventoPerfil,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.colorCard.copy(0.4f))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(80.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                val fotoUrl = evento.fotos?.firstOrNull()?.url
                AsyncImage(
                    model = fotoUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = evento.eventoNome ?: "Sem nome",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remover",
                            tint = Color.Red.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${formatarData(evento.eventoData)} às ${formatarHora(evento.horaInicio)}",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }

                Text(
                    text = evento.status ?: "Pendente",
                    fontSize = 12.sp,
                    color = when(evento.status?.lowercase()) {
                        "aprovado", "confirmado" -> Color.Green
                        "rejeitado", "recusado" -> Color.Red
                        else -> Color(0xFFFF8A50)
                    },
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = evento.descricao ?: "",
                    fontSize = 11.sp,
                    color = Color.LightGray,
                    maxLines = 2
                )
            }
        }
    }
}
