package com.example.sonara.features.artista.sobreEvento.ui
// Helper imports e correções

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HeaderUserSection
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.core.ui.theme.DarkGradients
import com.example.sonara.features.artista.sobreEvento.ui.components.EventMapView
import com.example.sonara.features.artista.sobreEvento.viewmodel.AboutEventViewModel
import com.example.sonara.features.home.components.ImageCarousel
import com.example.sonara.features.home.components.formatarData
import com.example.sonara.features.home.components.formatarHora

@Composable
fun AboutEventsScreen(
    eventId: Int,
    modifier: Modifier = Modifier,
    viewModel: AboutEventViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onApplyClick: (Int) -> Unit = {}
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
                    avatarUrl = uiState.userPhoto,
                    isLoggedIn = uiState.isLoggedIn
                ),
                onAvatarClick = {if (uiState.isLoggedIn) onNavigateToProfile() else onNavigateToLogin()},
                onNotificationClick = {}
            )
        }

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AppColors.PrimaryColor)
                }
            }
            uiState.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                    Text(uiState.errorMessage!!, color = Color.Gray, textAlign = TextAlign.Center)
                }
            }
            uiState.evento != null -> {
                val evento = uiState.evento!!

                // ── Card de Informações ──────────────────────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = AppColors.PrimaryColor
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Sobre o Evento",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.colorFontLogin
                        )

                        // Carrossel de Fotos
                        ImageCarousel(
                            fotos = evento.fotosUrls,
                            height = 200.dp,
                            modifier = Modifier.clip(RoundedCornerShape(12.dp))
                        )

                        // Avaliação (Exemplo com média real)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                            repeat(5) { index ->
                                val starColor = if (index < (evento.mediaAvaliacao ?: 0.0).toInt()) Color(0xFFFFD700) else Color.Gray
                                Icon(Icons.Default.Star, null, tint = starColor, modifier = Modifier.size(20.dp))
                            }
                            Text(
                                text = "(${evento.totalAvaliacoes})",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 12.sp
                            )
                        }

                        // Detalhes do Texto
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
                                .padding(16.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                EventDetailRow("Nome do Evento", evento.nome)
                                HorizontalDivider(color = Color.White)
                                EventDetailRow("Descrição", evento.descricao ?: "Sem descrição disponível")
                                HorizontalDivider(color = Color.White)
                                
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    EventDetailRow("DATA", formatarData(evento.data), Modifier.weight(1f))
                                    EventDetailRow("HORA", formatarHora(evento.horaInicio), Modifier.weight(1f), textAlign = TextAlign.End)
                                }
                            }
                        }
                    }
                }

                // ── Card de Localização ──────────────────────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.PrimaryColor
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Localização",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.colorFontLogin
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Rua: ${evento.logradouro ?: "N/A"}", color = Color.White.copy(alpha = 0.8f),fontWeight = FontWeight.Medium)
                            Text("Número: ${evento.numero ?: "S/N"}", color = Color.White.copy(alpha = 0.8f),fontWeight = FontWeight.Medium)
                            Text("Bairro: ${evento.bairro ?: "N/A"}", color = Color.White.copy(alpha = 0.8f),fontWeight = FontWeight.Medium)
                            Text("Cidade: ${evento.cidade ?: "N/A"} - ${evento.estado ?: ""}", color = Color.White.copy(alpha = 0.8f),fontWeight = FontWeight.Medium)
                        }

                        // Placeholder do Mapa
                        // ANTES — Placeholder estático
                        // DEPOIS — Mapa real com localização do evento
                        val latitude = evento.latitude?.toDouble()
                        val longitude = evento.longitude?.toDouble()

                        if (latitude != null && longitude != null) {
                            EventMapView(
                                latitude = latitude,
                                longitude = longitude,
                                title = evento.nome,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        } else {
                            // Fallback caso o evento não tenha coordenadas cadastradas
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .background(AppColors.SecondColor.copy(0.5f), RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Localização não disponível",
                                    color = Color.Red
                                )
                            }
                        }

                        // Botão de Inscrição
                        Button(
                            onClick = { onApplyClick(evento.id) },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AppColors.colorFontLogin)
                        ) {
                            Text("Inscreva-se", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
fun EventDetailRow(label: String, value: String, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Start) {
    Column(modifier = modifier) {
        Text(text = label.uppercase(), fontSize = 12.sp, color = AppColors.colorFontLogin,fontWeight = FontWeight.Medium, textAlign = textAlign, modifier = Modifier.fillMaxWidth())
        Text(text = value, fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Medium, textAlign = textAlign, modifier = Modifier.fillMaxWidth())
    }
}

