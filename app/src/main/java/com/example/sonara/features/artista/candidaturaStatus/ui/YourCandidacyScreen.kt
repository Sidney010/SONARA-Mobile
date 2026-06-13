package com.example.sonara.features.artista.candidaturaStatus.ui

import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HeaderUserSection
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.core.ui.theme.DarkGradients
import com.example.sonara.features.artista.candidaturaStatus.components.CandidacyInputField
import com.example.sonara.features.artista.candidaturaStatus.components.DividerItem
import com.example.sonara.features.artista.candidaturaStatus.components.InfoRow
import com.example.sonara.features.artista.candidaturaStatus.viewmodel.YourCandidacyViewModel
import com.example.sonara.features.home.components.formatarData
import com.example.sonara.features.home.components.formatarHora
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourCandidacyScreen(
    eventoId: Int,
    onNavigateToProfile: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    eventoArtistaId: Int? = null,
    onBack: () -> Unit,
    viewModel: YourCandidacyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val gradients = DarkGradients
    val pullToRefreshState = rememberPullToRefreshState()

    val statusRaw = uiState.eventoArtista?.status?.lowercase() ?: ""
    val isFinalized = statusRaw in listOf("aprovado", "confirmado", "rejeitado", "recusado", "convite aceito", "convite recusado", "aceito")

    var cacheEsperado by remember { mutableStateOf("") }
    var sobreArtista by remember { mutableStateOf("") }
    var motivoInscricao by remember { mutableStateOf("") }

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.loadData(eventoId, eventoArtistaId, isRefreshing = true)
        }
    }

    LaunchedEffect(uiState.isRefreshing) {
        if (uiState.isRefreshing) {
            pullToRefreshState.startRefresh()
        } else {
            pullToRefreshState.endRefresh()
        }
    }

    LaunchedEffect(eventoId, eventoArtistaId) {
        viewModel.loadData(eventoId, eventoArtistaId)
    }

    LaunchedEffect(uiState.eventoArtista) {
        uiState.eventoArtista?.let {
            cacheEsperado = it.cacheEsperado?.let { cache ->
                NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(cache)
                    .replace("R$", "").trim()
            } ?: ""
            sobreArtista = it.sobreArtista ?: ""
            motivoInscricao = it.motivoInscricao ?: ""
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess && uiState.successMessage != null) {
            Toast.makeText(context, uiState.successMessage, Toast.LENGTH_LONG).show()
            onBack()
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradients.secondaryCard)
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        ScreenContainer(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            padding = PaddingValues(start = 12.dp, end = 12.dp, top = 40.dp, bottom = 40.dp)
        ) {

            // ── Custom Detail Header with Back Arrow ─────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }

                HeaderUserSection(
                    state = HeaderUiState(
                        userName = uiState.userName,
                        userRole = uiState.userRole,
                        avatarUrl = uiState.userPhoto,
                        isLoggedIn = uiState.isLoggedIn
                    ),
                    onAvatarClick = {if (uiState.isLoggedIn) onNavigateToProfile() else onNavigateToLogin()},
                    onNotificationClick = {}
                )
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = AppColors.SecondColor
                )
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AppColors.colorCard.copy(0.5f))
                ) {
                    Column {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (uiState.eventoArtista == null) "Nova Candidatura" else "Minha Candidatura",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = AppColors.colorFontLogin
                                )
                                
                                if (uiState.eventoArtista != null && !isFinalized) {
                                    IconButton(onClick = { viewModel.deleteCandidacy() }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Cancelar Candidatura",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .background(
                                        Color.LightGray.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                            ) {
                                if (uiState.evento?.fotosUrls?.isNotEmpty() == true) {
                                    AsyncImage(
                                        model = uiState.evento?.fotosUrls?.first(),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = AppColors.SecondColor,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.CenterHorizontally),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = uiState.evento?.nome ?: "Carregando...",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.colorFontLogin
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .background(
                                        AppColors.PrimaryColor.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = uiState.evento?.descricao ?: "Sem descrição.",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .background(
                                AppColors.PrimaryColor.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp)
                    ) {
                        InfoRow(
                            label = "Cachê sugerido pelo contratante:",
                            value = if (uiState.eventoArtista?.cacheEsperado != null) "R$ ${uiState.eventoArtista?.cacheEsperado}" else "A definir"
                        )
                        DividerItem()

                        val isEditable = !isFinalized

                        CandidacyInputField(
                            label = "Cachê Esperado:",
                            value = cacheEsperado,
                            onValueChange = { input ->
                                if (isEditable) {
                                    val cleaned = input.replace(Regex("[^\\d]"), "")
                                    if (cleaned.isEmpty()) {
                                        cacheEsperado = ""
                                    } else {
                                        val parsed = cleaned.toDouble() / 100
                                        cacheEsperado = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                                            .format(parsed)
                                            .replace("R$", "")
                                            .trim()
                                    }
                                }
                            },
                            placeholder = "0,00",
                            enabled = isEditable,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            prefix = "R$ "
                        )
                        
                        CandidacyInputField(
                            label = "Sobre o Artista:",
                            value = sobreArtista,
                            onValueChange = { if (isEditable && it.length <= 500) sobreArtista = it },
                            placeholder = "Fale um pouco sobre você...",
                            enabled = isEditable,
                            maxChar = 500,
                            singleLine = false,
                            minLines = 3
                        )

                        CandidacyInputField(
                            label = "Motivo da Inscrição:",
                            value = motivoInscricao,
                            onValueChange = { if (isEditable && it.length <= 500) motivoInscricao = it },
                            placeholder = "Por que você quer participar?",
                            enabled = isEditable,
                            maxChar = 500,
                            singleLine = false,
                            minLines = 3
                        )

                        if (uiState.eventoArtista != null) {
                            DividerItem()

                            val statusColor = when (statusRaw) {
                                "aprovado", "confirmado", "convite aceito", "aceito" -> Color.Green
                                "rejeitado", "recusado", "convite recusado" -> Color.Red
                                else -> Color(0xFFFF8A50)
                            }

                            InfoRow(
                                label = "Status Atual:", 
                                value = uiState.eventoArtista?.status ?: "Pendente",
                                valueColor = statusColor
                            )
                            DividerItem()
                            InfoRow(label = "Cachê Ofertado:", value = "R$ ${uiState.eventoArtista?.cacheOfertado ?: "0.0"}")
                            DividerItem()
                            InfoRow(label = "Cachê Final:", value = "R$ ${uiState.eventoArtista?.cacheFinal ?: "0.0"}")
                            
                            if (statusRaw == "pendente") {
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { viewModel.acceptInvitation() },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                                    ) {
                                        Text("Aceitar", color = Color.White)
                                    }
                                    
                                    Button(
                                        onClick = { viewModel.refuseInvitation() },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                                    ) {
                                        Text("Recusar", color = Color.White)
                                    }
                                }
                            }
                        }

                        DividerItem()

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "DATA",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.colorFontLogin
                                )
                                Text(
                                    text = formatarData(uiState.evento?.data ?: "DD/MM/AAAA"),
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "HORA",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.colorFontLogin
                                )
                                Text(
                                    text = formatarHora(uiState.evento?.horaInicio ?: "00:00"),
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    if (!isFinalized) {
                        Button(
                            onClick = { 
                                viewModel.submitCandidacy(
                                    eventoId = eventoId,
                                    cacheEsperadoStr = cacheEsperado,
                                    sobreArtista = sobreArtista,
                                    motivoInscricao = motivoInscricao
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppColors.colorFontLogin,
                                contentColor = Color.White
                            )
                        ) {
                            Text(if (uiState.eventoArtista == null) "Inscrever-se" else "Atualizar")
                        }
                    }


                }
                Spacer(modifier = Modifier.height(45.dp))
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
            containerColor = Color(0xFF1A1A2E),
            contentColor = Color(0xFFFF710C)
        )
    }
}






