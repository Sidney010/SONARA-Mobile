package com.example.sonara.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.features.home.ui.Evento


/**
 * Componente dos Cards Menores exibidos em grade.
 */
@Composable
fun SmallEventCard(evento: Evento) {
    val configuration = LocalConfiguration.current
    // Calcula o tamanho dinâmico subtraindo as margens Laterais
    val itemWidth = (configuration.screenWidthDp.dp - 48.dp) / 3

    Card(
        modifier = Modifier
            .width(itemWidth)
            .height(160.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box {
            Box(modifier = Modifier.fillMaxSize().background(Color.Gray))

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                color = Color.Black.copy(alpha = 0.5f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Ver Mais",
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}