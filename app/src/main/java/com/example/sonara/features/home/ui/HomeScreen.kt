package com.example.sonara.features.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.sonara.features.home.components.EventHighlightCard
import com.example.sonara.features.home.components.HomeEmptyState
import com.example.sonara.features.home.components.HomeSearchBar
import com.example.sonara.features.home.components.SmallEventCard

@Composable
fun HomeScreen(
    eventos: List<Evento> = emptyList(),
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenContainer(
        verticalArrangement = Arrangement.Top,
        verticalSpacing = 16.dp,
        padding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
    ) {

        val headerState = HeaderUiState(
            userName = "UserName",
            userRole = "TipodeUsuario"
        )

        HomeHeader(
            state = headerState,
            onLogoClick = {},
            onAvatarClick = {},
            onNotificationClick = {}
        )

        HomeSearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (eventos.isNotEmpty()) {
                item {
                    Text(
                        text = "Eventos próximos",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Start
                    )
                }

                item {
                    EventHighlightCard(evento = eventos.first())
                }

                // Sub-lista com os eventos secundários organizados em linhas estáveis
                val secondaryEvents = eventos.drop(1)
                val rows = secondaryEvents.chunked(3)

                items(rows) { rowEvents ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowEvents.forEach { evento ->
                            SmallEventCard(evento = evento)
                        }
                    }
                }

                // Espaçamento final de segurança para evitar cortes por navegação
                item {
                    Spacer(modifier = Modifier.height(86.dp))
                }

            } else {
                // Caso a lista esteja vazia, renderiza o Empty State limpo
                item {
                    HomeEmptyState()
                }
            }
        }
    }
}

/**
 * Entidade que modela o domínio do Evento no SONARA.
 */
data class Evento(
    val id: String = java.util.UUID.randomUUID().toString(),
    val titulo: String = "",
    val imagemUrl: String = "",
    val categoria: String = "Show"
)