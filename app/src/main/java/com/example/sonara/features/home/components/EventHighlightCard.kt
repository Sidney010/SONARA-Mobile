package com.example.sonara.features.home.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.domain.model.Evento

/**
 * Componente do Card de Destaque principal.
 */
@Composable
fun EventHighlightCard(
    evento: Evento,
    isLoggedIn: Boolean,
    onVerMaisClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Carrossel (ou placeholder)
            ImageCarousel(
                fotos = evento.fotosUrls,
                height = 240.dp,
                placeholderColor = Color(0xFF1A1A2E)
            )

            // Gradiente overlay para legibilidade do texto
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.5f to Color.Black.copy(alpha = 0.2f),
                            1f to Color.Black.copy(alpha = 0.75f)
                        )
                    )
            )

            // Informações na parte inferior
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text  = evento.nome,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Data
                    Text(
                        text  = formatarData(evento.data),
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )

                    if (!evento.horaInicio.isNullOrBlank()) {
                        Text("•", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                        Text(
                            text  = formatarHora(evento.horaInicio),
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    }

                    if (!evento.cidade.isNullOrBlank()) {
                        Text("•", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                        Icon(Icons.Default.LocationOn, null,
                            tint = Color.White.copy(alpha = 0.85f),
                            modifier = Modifier.size(12.dp))
                        Text(
                            text  = "${evento.cidade}/${evento.estado}",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    }
                }

                if (!evento.artista.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MusicNote, null,
                            tint = AppColors.PrimaryOrange,
                            modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text  = evento.artista,
                            color = AppColors.PrimaryOrange,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Botão "Ver Mais" — desabilitado se anônimo
            Button(
                onClick  = onVerMaisClick,
                enabled  = isLoggedIn,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor = AppColors.PrimaryOrange,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
                ),
                shape    = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text  = if (isLoggedIn) "Ver mais" else "Faça login",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}