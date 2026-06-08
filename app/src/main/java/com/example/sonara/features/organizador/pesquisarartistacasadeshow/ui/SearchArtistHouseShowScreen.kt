package com.example.sonara.features.organizador.pesquisarartistacasadeshow.ui

import ArtistCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.AppColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchArtistHouseshowScreen(
    modifier: Modifier = Modifier,
    eventos: List<Evento> = EventoMock.listaComEventos
) {
    ScreenContainer(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        verticalSpacing = 6.dp,
        padding = PaddingValues(12.dp, 40.dp)
    ) {
        HomeHeader(
            state = HeaderUiState(userName = "Anônimo", userRole = "Usuário"),
            onLogoClick = {},
            onAvatarClick = {},
            onNotificationClick = {}
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            TextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp)),
                placeholder = {
                    Text(text = "Pesquisar", color = Color.White, fontSize = 16.sp)
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.fillMaxHeight(0.8f),
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.fillMaxHeight(0.8f),
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                shape = CircleShape
            )
        }

        if (eventos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum Artista cadastrado",
                    color = AppColors.colorFontLogin,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center


                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Artistas Próximos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.colorFontLogin
                )

                val totalArtists = 9
                val rows = (totalArtists + 2) / 3

                repeat(rows) { rowIndex ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val startIndex = rowIndex * 3
                        val endIndex = minOf(startIndex + 3, totalArtists)

                        repeat(endIndex - startIndex) {
                            ArtistCard(
                                modifier = Modifier.weight(1f),
                                filledStars = when (it % 3) {
                                    0 -> 4
                                    1 -> 4
                                    else -> 3
                                }
                            )
                        }
                        repeat(3 - (endIndex - startIndex)) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

data class Evento(
    val id: String = java.util.UUID.randomUUID().toString(),
    val titulo: String,
    val imagemUrl: String = "",
    val categoria: String = "Show"
)

object EventoMock {
    val listaComEventos = listOf(
        Evento(titulo = "Grande Show Amarelo", categoria = "Destaque"),
        Evento(titulo = "Evento de Rock 1"),
        Evento(titulo = "Evento de Rock 2"),
        Evento(titulo = "Evento de Rock 3"),
        Evento(titulo = "Evento de Rock 4"),
        Evento(titulo = "Evento de Rock 5"),
        Evento(titulo = "Evento de Rock 6"),
        Evento(titulo = "Evento de Rock 7"),
        Evento(titulo = "Evento de Rock 8"),
        Evento(titulo = "Evento de Rock 9")
    )

    val listaVazia = emptyList<Evento>()
}
