package com.example.sonara.features.organizador.homeorganizador.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.example.sonara.features.home.components.SmallEventCard
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.DarkGradients
import com.example.sonara.features.organizador.homeorganizador.viewmodel.HomeOrganizerViewModel

@Composable
fun HomeOrganizerScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeOrganizerViewModel = hiltViewModel(),
    onNavigateToCreateEvent: () -> Unit = {},
    onNavigateToHireArtist: () -> Unit = {},
    onNavigateToMyEvents: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToEventDetails: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val gradients = DarkGradients

    ScreenContainer(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        verticalSpacing = 16.dp,
        padding = PaddingValues(start = 12.dp, end = 12.dp, top = 40.dp, bottom = 130.dp)
    ) {
        // Header com dados reais da sessão
        HomeHeader(
            state = HeaderUiState(
                userName = uiState.userName,
                userRole = uiState.userRole,
                avatarUrl = uiState.userPhoto,
                isLoggedIn = true
            ),
            onLogoClick = {},
            onAvatarClick = onNavigateToProfile,
            onNotificationClick = {}
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .background(brush = gradients.secondaryCard)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Saudação personalizada
                val displayFirstName = uiState.userName.split(" ").firstOrNull() ?: "Organizador"
                Text(
                    text = "Olá $displayFirstName, o que vamos fazer hoje?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OrganizerActionBox(
                        text = "Criar Evento",
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToCreateEvent
                    )

                    OrganizerActionBox(
                        text = "Contratar Artista",
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToHireArtist
                    )
                }

                OrganizerActionBox(
                    text = "Gerenciar Meus Eventos",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onNavigateToMyEvents
                )

                if (uiState.events.isNotEmpty()) {
                    Text(
                        text = "Meus Próximos Eventos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 8.dp)
                    ) {
                        items(uiState.events) { evento ->
                            SmallEventCard(
                                evento = evento,
                                width = 150.dp,
                                isLoggedIn = true,
                                onClick = { onNavigateToEventDetails(evento.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrganizerActionBox(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(140.dp)
            .background(
                Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}
