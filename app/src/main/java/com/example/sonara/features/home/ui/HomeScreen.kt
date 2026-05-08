package com.example.sonara.features.home.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.components.navigation.BottomNavigationBar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    eventos: List<Evento> = EventoMock.listaComEventos
) {
    ScreenContainer(
        verticalArrangement = Arrangement.SpaceBetween,
        verticalSpacing = 6.dp,
        padding = PaddingValues(12.dp,40.dp)
    ) {
        val headerState = HeaderUiState(
            userName = "Sidney Campos Aragão",
            userRole = "Usuário"
        )

        HomeHeader(
            state = headerState,
            onLogoClick = {},
            onAvatarClick = {},
            onNotificationClick = {}
        )

        // Search
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)

        ) {
            TextField(
                value = "",
                onValueChange = { /*TODO*/ },
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp)), // Garante o formato de pílula
                placeholder = {
                    Text(
                        text = "Pesquisar",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.fillMaxHeight(0.8f),
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.fillMaxHeight(0.8f),
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true,
                shape = CircleShape // Formato arredondado
            )
        }

        // Main
        // Header
        // Title
        Text( text = "Eventos próximos", color = Color.White, fontSize = 18.sp)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .imePadding()
                .padding(0.dp, 0.dp, 0.dp, 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (eventos.isEmpty()) {

                item {
                    // Estado Vazio
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhum evento cadastrado",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            } else {

                // Destaque (Primeiro item da lista)
                item {
                    EventHighlightCard(eventos.first())
                }

                // Grade de Eventos
                item {

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        maxItemsInEachRow = 3,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        val secondaryEvents = eventos.drop(1)

                        secondaryEvents.forEach { evento ->
                            SmallEventCard(evento)
                        }
                    }
                }
                item{
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }

}



@Composable
fun EventHighlightCard(evento: Evento) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            // Imagem de Fundo (Simulada)
            Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))

            // Botão "Ver Mais" centralizado na parte inferior
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.7f)),
                shape = CircleShape
            ) {
                Text("Ver Mais", color = Color.White)
            }
        }
    }
}

@Composable
fun SmallEventCard(evento: Evento) {
    // Proporção de tela para caber 3 por linha
    val itemWidth = (LocalConfiguration.current.screenWidthDp.dp - 40.dp) / 3

    Card(
        modifier = Modifier
            .width(itemWidth)
            .height(160.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box {
            Box(modifier = Modifier.fillMaxSize().background(Color.Gray))

            // Overlay do botão menor
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                color = Color.Black.copy(alpha = 0.5f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    "Ver Mais",
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}

data class Evento(
    val id: String = java.util.UUID.randomUUID().toString(),
    val titulo: String,
    val imagemUrl: String = "", // Aqui usarias URLs reais ou IDs de recursos
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