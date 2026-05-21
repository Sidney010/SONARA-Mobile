package com.example.sonara.features.meusEventos.ui

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.DarkGradients

@Composable
fun MyEventsScreen(modifier: Modifier = Modifier) {

    val gradients = DarkGradients

    ScreenContainer(
        verticalArrangement = Arrangement.SpaceBetween,
        verticalSpacing = 6.dp,
        padding = PaddingValues(12.dp,40.dp)
    ) {
        val headerState = HeaderUiState(
            userName = "Anônimo",
            userRole = "Usuário"
        )

        HomeHeader(
            state = headerState,
            onLogoClick = {},
            onAvatarClick = {},
            onNotificationClick = {}
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(12.dp)
                    .background(brush = gradients.secondaryCard, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    eventoCardItem()
                    eventoCardItem()
                    eventoCardItem()
                }
            }
        }
    }
}

@Composable
fun eventoCardItem(modifier: Modifier = Modifier) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xFF3B0A00), shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Box da imagem (esquerda)
        Box(
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .background(Color.Gray, shape = RoundedCornerShape(8.dp))
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Nome do Evento",
                fontSize = 14.sp,
                color = Color.White
            )
            Text(
                text = "22/09/2026   23:00",
                fontSize = 12.sp,
                color = Color.White
            )
            Text(
                text = "Descrição do evento aqui",
                fontSize = 11.sp,
                color = Color.White
            )
        }
    }
}
