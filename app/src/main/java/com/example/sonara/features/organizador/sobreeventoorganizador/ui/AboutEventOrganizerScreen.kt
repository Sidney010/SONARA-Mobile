package com.example.sonara.features.organizador.sobreeventoorganizador.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HeaderUserSection
import com.example.sonara.core.ui.theme.DarkGradients
import com.example.sonara.features.home.components.ImageCarousel
import com.example.sonara.features.home.components.formatarData
import com.example.sonara.features.home.components.formatarHora
import com.example.sonara.features.organizador.sobreeventoorganizador.viewmodel.AboutEventOrganizerViewModel

@Composable
fun AboutEventOrganizerScreen(
    eventId: Int,
    modifier: Modifier = Modifier,
    viewModel: AboutEventOrganizerViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val gradients = DarkGradients

    LaunchedEffect(eventId) {
        viewModel.loadEvento(eventId)
    }

    ScreenContainer(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        padding = PaddingValues(start = 12.dp, end = 12.dp, top = 40.dp, bottom = 40.dp)
    ) {
        // ── Custom Detail Header with Back Arrow ─────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White
                )
            }

            HeaderUserSection(
                state = HeaderUiState(
                    userName = uiState.userName,
                    userRole = uiState.userRole,
                    avatarUrl = uiState.userPhoto
                ),
                onAvatarClick = {if (uiState.isLoggedIn) onNavigateToProfile() else onNavigateToLogin()},
                onNotificationClick = {}
            )
        }

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFF710C))
                }
            }
            uiState.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                    Text(uiState.errorMessage!!, color = Color.Gray, textAlign = TextAlign.Center)
                }
            }
            uiState.evento != null -> {
                val evento = uiState.evento!!

                // Card Detalhes do Evento
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Column(
                        modifier = Modifier.background(brush = gradients.secondaryCard).padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Sobre o Evento", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)

                        ImageCarousel(
                            fotos = evento.fotosUrls,
                            height = 200.dp,
                            modifier = Modifier.clip(RoundedCornerShape(12.dp))
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                            repeat(5) { index ->
                                val starColor = if (index < (evento.mediaAvaliacao ?: 0.0).toInt()) Color(0xFFFFD700) else Color.Gray
                                Icon(Icons.Default.Star, null, tint = starColor, modifier = Modifier.size(20.dp))
                            }
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth().background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp)).padding(16.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                EventDetailRow("Nome do Evento", evento.nome)
                                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                                EventDetailRow("Descrição", evento.descricao ?: "Sem descrição")
                                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    EventDetailRow("DATA", formatarData(evento.data), Modifier.weight(1f))
                                    EventDetailRow("HORA", formatarHora(evento.horaInicio), Modifier.weight(1f), textAlign = TextAlign.End)
                                }
                            }
                        }
                    }
                }

                // Card Artista (Novo)
                evento.artista?.let { artista ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier.background(brush = gradients.secondaryCard).padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text("Sobre o Artista", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                AsyncImage(
                                    model = artista.foto,
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp).clip(CircleShape).background(Color.Gray),
                                    contentScale = ContentScale.Crop
                                )
                                Column {
                                    Text(artista.nome ?: "Nome não informado", color = Color.White, fontWeight = FontWeight.Medium)
                                    Text(artista.sobre ?: "Sem descrição", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                // Card Localização
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Column(
                        modifier = Modifier.background(brush = gradients.secondaryCard).padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Localização", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Rua: ${evento.logradouro ?: "N/A"}", color = Color.White.copy(alpha = 0.8f))
                            Text("Cidade: ${evento.cidade ?: "N/A"} - ${evento.estado ?: ""}", color = Color.White.copy(alpha = 0.8f))
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth().height(150.dp).background(Color(0xFFC8DBB0), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.LocationOn, "Mapa", tint = Color.Gray, modifier = Modifier.size(40.dp))
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
private fun EventDetailRow(label: String, value: String, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Start) {
    Column(modifier = modifier) {
        Text(label.uppercase(), fontSize = 10.sp, color = Color.White.copy(alpha = 0.5f), textAlign = textAlign, modifier = Modifier.fillMaxWidth())
        Text(value, fontSize = 14.sp, color = Color(0xFFFFAA70), fontWeight = FontWeight.Medium, textAlign = textAlign, modifier = Modifier.fillMaxWidth())
    }
}
