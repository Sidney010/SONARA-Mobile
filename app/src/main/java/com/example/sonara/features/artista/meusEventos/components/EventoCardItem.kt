package com.example.sonara.features.artista.meusEventos.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.domain.model.usuarioperfil.UsuarioEventoPerfil
import com.example.sonara.features.home.components.formatarData
import com.example.sonara.features.home.components.formatarHora

@Composable
fun EventoCardItem(
    evento: UsuarioEventoPerfil,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val status = evento.status?.lowercase() ?: ""
                val isApproved = listOf("aprovado", "confirmado", "convite aceito", "aceito").any {
                    status.contains(it)
                }
                if (isApproved) {
                    Toast.makeText(
                        context,
                        "Eventos já confirmados não podem ser alterados",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    onClick()
                }
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.colorCard.copy(0.4f))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(80.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                val fotoUrl = evento.fotos?.firstOrNull()?.url
                AsyncImage(
                    model = fotoUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = evento.eventoNome ?: "Sem nome",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = {
                        val status = evento.status?.lowercase() ?: ""
                        val isApproved = listOf("aprovado", "confirmado", "convite aceito", "aceito").any {
                            status.contains(it)
                        }
                        if (isApproved) {
                            Toast.makeText(
                                context,
                                "Eventos já confirmados não podem ser alterados",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            onDelete()
                        }
                    }) {
                        val status = evento.status?.lowercase() ?: ""
                        val isApproved = listOf("aprovado", "confirmado", "convite aceito", "aceito").any {
                            status.contains(it)
                        }
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remover",
                            tint = if (isApproved)
                                Color.Gray.copy(alpha = 0.5f)
                            else
                                Color.Red.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${formatarData(evento.eventoData)} às ${formatarHora(evento.horaInicio)}",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }

                Text(
                    text = evento.status ?: "Pendente",
                    fontSize = 12.sp,
                    color = when(evento.status?.lowercase()) {
                        "aprovado", "confirmado","convite aceito" -> Color.Green
                        "rejeitado", "recusado", "convite recusado" -> Color.Red
                        else -> Color(0xFFFF8A50)
                    },
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = evento.descricao ?: "",
                    fontSize = 11.sp,
                    color = Color.LightGray,
                    maxLines = 2
                )
            }
        }
    }
}