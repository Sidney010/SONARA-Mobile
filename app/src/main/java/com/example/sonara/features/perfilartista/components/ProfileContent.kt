package com.example.sonara.features.perfilartista.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.domain.model.UsuarioPerfil
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ProfileContent(perfil: UsuarioPerfil, isEditing: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── Avatar ────────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFF2D2D3A)),
            contentAlignment = Alignment.Center
        ) {
            val fotoUrl = perfil.fotosUrls.firstOrNull()
            if (fotoUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(fotoUrl).crossfade(true).build(),
                    contentDescription = "Foto de perfil",
                    modifier     = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = null,
                    tint     = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        // ── Nome e tipo ───────────────────────────────────────────────────────
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text       = perfil.nome,
                color      = Color.White,
                fontSize   = 22.sp,
                fontWeight = FontWeight.Bold
            )
            if (!perfil.nomeArtistico.isNullOrBlank()) {
                Text(
                    text   = "\"${perfil.nomeArtistico}\"",
                    color  = AppColors.PrimaryOrange,
                    fontSize = 14.sp
                )
            }
            if (!perfil.tipoUsuario.isNullOrBlank()) {
                Surface(
                    color = AppColors.PrimaryOrange.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text     = perfil.tipoUsuario.replaceFirstChar { it.uppercase() },
                        color    = AppColors.PrimaryOrange,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // ── Bio / Descrição ───────────────────────────────────────────────────
        if (!perfil.descricao.isNullOrBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors   = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E)),
                shape    = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text     = perfil.descricao,
                    color    = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // ── Informações pessoais ──────────────────────────────────────────────
        ProfileSection(title = "Informações") {
            ProfileInfoRow(Icons.Outlined.Email, "Email", perfil.email)

            if (!perfil.telefone.isNullOrBlank())
                ProfileInfoRow(Icons.Outlined.Phone, "Telefone", perfil.telefone)

            if (!perfil.cpf.isNullOrBlank())
                ProfileInfoRow(Icons.Outlined.Badge, "CPF",
                    perfil.cpf.let {
                        // Máscara visual: 123.456.789-00
                        val d = it.filter { c -> c.isDigit() }
                        if (d.length == 11)
                            "${d.take(3)}.${d.drop(3).take(3)}.${d.drop(6).take(3)}-${d.drop(9)}"
                        else it
                    }
                )

            if (!perfil.dataNasc.isNullOrBlank()) {
                val dataFormatada = runCatching {
                    ZonedDateTime.parse(perfil.dataNasc)
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR")))
                }.getOrElse { perfil.dataNasc.take(10) }
                ProfileInfoRow(Icons.Outlined.Cake, "Nascimento", dataFormatada)
            }

            if (!perfil.criado.isNullOrBlank()) {
                val membroDesde = runCatching {
                    ZonedDateTime.parse(perfil.criado)
                        .format(DateTimeFormatter.ofPattern("MMMM 'de' yyyy", Locale("pt", "BR")))
                }.getOrElse { "" }
                if (membroDesde.isNotBlank())
                    ProfileInfoRow(Icons.Outlined.CalendarToday, "Membro desde", membroDesde)
            }
        }

        // ── Aviso de edição futura ────────────────────────────────────────────
        if (isEditing) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors   = CardDefaults.cardColors(containerColor = Color(0xFF2D2D3A)),
                shape    = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Outlined.Info, null, tint = AppColors.PrimaryOrange)
                    Text(
                        text  = "A edição completa do perfil via PUT estará disponível em breve.",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}