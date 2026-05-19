package com.example.sonara.features.home.components
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

// ─────────────────────────────────────────────────────────────────────────────
// Utilitários de formatação de data/hora
// ─────────────────────────────────────────────────────────────────────────────

fun formatarData(isoDate: String?): String {
    if (isoDate.isNullOrBlank()) return "Data a confirmar"
    return runCatching {
        val zdt = ZonedDateTime.parse(isoDate)
        zdt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR")))
    }.getOrElse { isoDate.take(10) }
}

fun formatarHora(hora: String?): String {
    if (hora.isNullOrBlank()) return ""
    return hora.take(5)   // "09:00" de "09:00:00"
}


// ─────────────────────────────────────────────────────────────────────────────
// Carrossel de imagens reutilizável
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(
    fotos: List<String>,
    modifier: Modifier = Modifier,
    height: Dp = 220.dp,
    placeholderColor: Color = Color.DarkGray
) {
    if (fotos.isEmpty()) {
        // Placeholder quando não há fotos
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .background(placeholderColor)
        )
        return
    }

    val pagerState = rememberPagerState(pageCount = { fotos.size })

    Box(modifier = modifier.fillMaxWidth().height(height)) {
        HorizontalPager(
            state    = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fotos[page])
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagem do evento",
                modifier     = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Indicador de páginas (bolinhas) — só mostra se há mais de 1 foto
        if (fotos.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(fotos.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == pagerState.currentPage) 8.dp else 6.dp)
                            .background(
                                color = if (index == pagerState.currentPage) Color.White
                                else Color.White.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}