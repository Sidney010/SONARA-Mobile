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
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.AppButton
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HeaderUserSection
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.core.ui.theme.DarkGradients
import com.example.sonara.features.artista.candidaturaStatus.viewmodel.YourCandidacyViewModel

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
    
    var cacheEsperado by remember { mutableStateOf("") }
    var sobreArtista by remember { mutableStateOf("") }
    var motivoInscricao by remember { mutableStateOf("") }

    LaunchedEffect(eventoId, eventoArtistaId) {
        viewModel.loadData(eventoId, eventoArtistaId)
    }

    LaunchedEffect(uiState.eventoArtista) {
        uiState.eventoArtista?.let {
            cacheEsperado = it.cacheEsperado?.toString() ?: ""
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
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (uiState.eventoArtista == null) "Nova Candidatura" else "Minha Candidatura",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = AppColors.colorFontLogin
                                )
                                
                                if (uiState.eventoArtista != null) {
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

                        CacheInputField(
                            label = "Seu Cachê Esperado:",
                            value = cacheEsperado,
                            onValueChange = { cacheEsperado = it },
                            placeholder = "Digite aqui..."
                        )
                        
                        CacheInputField(
                            label = "Sobre Você:",
                            value = sobreArtista,
                            onValueChange = { sobreArtista = it },
                            placeholder = "Fale um pouco sobre você..."
                        )

                        CacheInputField(
                            label = "Motivo da Inscrição:",
                            value = motivoInscricao,
                            onValueChange = { motivoInscricao = it },
                            placeholder = "Por que você quer participar?"
                        )

                        if (uiState.eventoArtista != null) {
                            DividerItem()
                            InfoRow(label = "Cachê Ofertado:", value = "R$ ${uiState.eventoArtista?.cacheOfertado ?: "0.0"}")
                            DividerItem()
                            InfoRow(label = "Cachê Final:", value = "R$ ${uiState.eventoArtista?.cacheFinal ?: "0.0"}")
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
                                    text = uiState.evento?.data ?: "DD/MM/AAAA",
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
                                    text = uiState.evento?.horaInicio ?: "00:00",
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { 
                            viewModel.submitCandidacy(
                                eventoId = eventoId,
                                cacheEsperado = cacheEsperado.toDoubleOrNull() ?: 0.0,
                                sobreArtista = sobreArtista,
                                motivoInscricao = motivoInscricao
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        enabled = !uiState.isLoading,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.colorFontLogin,
                            contentColor = Color.White
                        )
                   ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        } else {
                            Text(if (uiState.eventoArtista == null) "Inscrever-se" else "Atualizar")
                        }
                    }


                }
                Spacer(modifier = Modifier.height(45.dp))
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.colorFontLogin
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun DividerItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.White.copy(alpha = 0.12f))
    )
}


@Composable
fun CacheInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Digite aqui...",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.colorFontLogin
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.LightGray.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                cursorBrush = SolidColor(Color(0xFFFFAA70)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}
