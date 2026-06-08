package com.example.sonara.features.organizador.meuseventosorganizador.ui


import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
<<<<<<< Updated upstream
import androidx.compose.ui.layout.ContentScale
=======
>>>>>>> Stashed changes
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
import com.example.sonara.domain.model.Evento
import com.example.sonara.features.home.components.formatarData
import com.example.sonara.features.home.components.formatarHora
import com.example.sonara.features.organizador.meuseventosorganizador.viewmodel.MyEventsOrganizerViewModel

@Composable
fun MyEventsOrganizerScreen(
    modifier: Modifier = Modifier,
    viewModel: MyEventsOrganizerViewModel = hiltViewModel(),
    onEventClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
<<<<<<< Updated upstream
    val uiState by viewModel.uiState.collectAsState()
    val gradients = DarkGradients
=======

>>>>>>> Stashed changes

    ScreenContainer(
        modifier = Modifier,
        verticalArrangement = Arrangement.Top,
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

<<<<<<< Updated upstream
        Spacer(modifier = Modifier.height(16.dp))
=======
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Card(
                 modifier = Modifier
                        .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                       color = AppColors.PrimaryColor.copy(0.5f).copy(0.5f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Meus Eventos",
                        color = AppColors.colorFontLogin,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
>>>>>>> Stashed changes

        Text(
            "Meus Eventos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFFF710C))
            }
        } else if (uiState.errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(uiState.errorMessage!!, color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(uiState.eventos) { evento ->
                    EventoCardItem(
                        evento = evento,
                        onClick = { onEventClick(evento.id) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
<<<<<<< Updated upstream
fun EventoCardItem(
    evento: Evento,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradients = DarkGradients
=======
fun eventoCardItem(modifier: Modifier = Modifier) {

>>>>>>> Stashed changes

    Card(
        modifier = modifier
            .fillMaxWidth()
<<<<<<< Updated upstream
            .clickable(onClick = onClick),
=======
            .wrapContentHeight()
            .background(
              color = AppColors.colorCard.copy(0.5f),
                shape = RoundedCornerShape(16.dp)
            ),
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
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
=======
            Text(
                text = "Nome do Evento",
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "22/09/2026   23:00",
                fontSize = 12.sp,
                color = AppColors.colorFontLogin,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Descrição do evento aqui",
                fontSize = 11.sp,
                color = AppColors.colorFontLogin,
                fontWeight = FontWeight.Bold
            )

>>>>>>> Stashed changes
        }
    }
}
