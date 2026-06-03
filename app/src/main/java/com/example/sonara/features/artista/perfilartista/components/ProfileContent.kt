package com.example.sonara.features.artista.perfilartista.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.domain.model.usuarioperfil.UsuarioCachePerfil
import com.example.sonara.domain.model.usuarioperfil.UsuarioPerfil
import com.example.sonara.domain.model.usuarioperfil.UsuarioEventoPerfil
import com.example.sonara.domain.model.Fotos
import com.example.sonara.domain.model.usuarioperfil.UsuarioPerfilEventosEndereco

@Composable
fun ProfileContent(
    perfil: UsuarioPerfil,
    isEditing: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ── Foto + nome + tipo ────────────────────────────────────────
        ProfileHeader(perfil = perfil)

        // ── Dados do artista (nome artístico, descrição, avaliação) ──
        if (perfil.tipoUsuario == "Artista") {
            perfil.artista?.let { artista ->
                ProfileCard(title = "Perfil Artístico") {
                    InfoRow(label = "Nome artístico", value = artista.nomeArtistico ?: "-")
                    InfoRow(label = "Descrição",      value = artista.descricao ?: "-")

                    // Avaliação
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = AppColors.SecondColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        val media = artista.mediaAvaliacao
                        Text(
                            text = if (media != null) "%.1f".format(media) +
                                    " (${artista.totalAvaliacoes} avaliações)"
                            else "Sem avaliações",
                            fontSize = 13.sp,
                            color = Color.LightGray
                        )
                    }

                    // Gêneros musicais
                    if (artista.generosMusicais.isNotEmpty()) {
                        Text("Gêneros musicais",
                            fontSize = 12.sp, color = AppColors.colorFontLogin,
                            modifier = Modifier.padding(top = 4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(artista.generosMusicais) { genero ->
                                SuggestionChip(
                                    onClick = {},
                                    label = { Text(genero.nome, fontSize = 11.sp) },
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        containerColor = AppColors.PrimaryColor.copy(alpha = 0.15f),
                                        labelColor = AppColors.PrimaryColor
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // ── Dados do organizador ──────────────────────────────────────
        if (perfil.tipoUsuario == "Organizador") {
            ProfileCard(title = "Meus Dados") {
                InfoRow(label = "Nome",     value = perfil.nome)
                InfoRow(label = "Email",    value = perfil.email)
                InfoRow(label = "Telefone", value = perfil.telefone ?: "-")
            }
        }

        // ── Dados básicos (Usuario) ───────────────────────────────────
        if (perfil.tipoUsuario == "Usuario") {
            ProfileCard(title = "Meus Dados") {
                InfoRow(label = "Nome",  value = perfil.nome)
                InfoRow(label = "Email", value = perfil.email)
            }
        }

        // ── Endereço ─────────────────────────────────────────────────
        perfil.endereco?.let { end ->
            ProfileCard(title = "Endereço") {
                InfoRow(label = "CEP",         value = end.cep)
                InfoRow(label = "Rua",         value = end.rua)
                InfoRow(label = "Cidade / UF", value = "${end.cidade} / ${end.uf}")
                InfoRow(label = "Bairro",      value = end.bairro)
            }
        }

        // ── Redes sociais ─────────────────────────────────────────────
        if (perfil.redesSociais.isNotEmpty()) {
            ProfileCard(title = "Redes Sociais") {
                perfil.redesSociais.forEach { rs ->
                    InfoRow(label = rs.tipoNome ?: "Link", value = rs.link)
                }
            }
        }

        // ── Eventos ──────────────────────────────────────────────────
        val eventos: List<UsuarioEventoPerfil> = when (perfil.tipoUsuario) {
            "Artista" -> perfil.artista?.eventos?.filterNotNull() ?: emptyList()

            "Organizador" -> perfil.organizador?.eventos
                ?.mapNotNull { eventoDto ->            // <- mapNotNull protege nulos
                    try {
                        UsuarioEventoPerfil(
                            idEvento        = eventoDto.idEvento,
                            eventoData      = eventoDto.eventoData,
                            eventoNome      = eventoDto.eventoNome,
                            cache           = eventoDto.cache?.let {
                                UsuarioCachePerfil(
                                    it.cacheFinal, it.cacheEsperado,
                                    it.cacheOfertado, it.cacheProposta
                                )
                            },
                            status          = eventoDto.status?.nome,
                            fotos           = eventoDto.fotos
                                ?.mapNotNull { f -> f?.let { Fotos(it.idFoto, it.url) } }
                                ?: emptyList(),
                            endereco        = eventoDto.endereco?.let {
                                UsuarioPerfilEventosEndereco(
                                    cep = it.cep, bairro = it.bairro, cidade = it.cidade,
                                    estado = it.estado, numero = it.numero,
                                    latitude = it.latitude, longitude = it.longitude,
                                    logradouro = it.logradouro, complemento = it.complemento,
                                    idEnderecoEvento = it.idEnderecoEvento
                                )
                            },
                            horaFim = eventoDto.horaFim, descricao = eventoDto.descricao,
                            horaInicio = eventoDto.horaInicio, sobreArtista = null,
                            motivoInscricao = null, idEventoArtista = eventoDto.idEventoArtista
                        )
                    } catch (e: Exception) { null }    // <- nunca deixa crashar a tela
                } ?: emptyList()

            else -> emptyList()
        }

        if (eventos.isNotEmpty()) {
            ProfileCard(title = "Meus Eventos") {
                eventos.forEach { evento ->
                    EventoItem(evento = evento)
                    if (evento != eventos.last()) {
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    }
                }
            }
        }
    }
}

// ── Subcomponentes ────────────────────────────────────────────────────────────

@Composable
private fun ProfileHeader(perfil: UsuarioPerfil) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box {
            if (perfil.foto != null) {
                AsyncImage(
                    model = perfil.foto,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null,
                        tint = Color.Gray, modifier = Modifier.size(40.dp))
                }
            }
        }

        Text(perfil.nome, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)

        // Nome artístico abaixo do nome, se artista
        perfil.artista?.nomeArtistico?.let {
            Text("\"$it\"", fontSize = 14.sp, color = Color.LightGray)
        }

        Surface(
            shape = RoundedCornerShape(20.dp),
            color = AppColors.SecondColor.copy(alpha = 0.2f)
        ) {
            Text(
                text = perfil.tipoUsuario,
                fontSize = 12.sp,
                color = AppColors.colorFontLogin,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun ProfileCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.colorCard.copy(0.5f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(title, fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold, color = Color.White)
            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
            content()
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(label, fontSize = 11.sp, color = Color.Gray)
        Text(value, fontSize = 14.sp, color = Color.White)
    }
}

@Composable
private fun EventoItem(evento: UsuarioEventoPerfil) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(evento.eventoNome ?: "Evento", fontSize = 14.sp,
            fontWeight = FontWeight.Medium, color = AppColors.colorFontLogin)

        val local = listOfNotNull(
            evento.endereco?.cidade,
            evento.endereco?.estado
        ).joinToString(" / ")

        if (local.isNotBlank())
            Text(local, fontSize = 12.sp, color = Color.Gray)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            evento.eventoData?.let {
                Text(it.take(10), fontSize = 12.sp, color = Color.LightGray)
            }
            evento.horaInicio?.let {
                Text(it.take(5), fontSize = 12.sp, color = Color.LightGray)
            }
        }

        // Foto do evento (primeira disponível)
        val primeiraFoto = evento.fotos?.firstOrNull()?.url
        if (primeiraFoto != null) {
            AsyncImage(
                model = primeiraFoto,
                contentDescription = "Foto do evento",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}