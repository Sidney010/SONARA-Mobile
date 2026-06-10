package com.example.sonara.features.organizador.criareventoorganizador.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.AppTextField
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.features.organizador.criareventoorganizador.viewmodel.CreateEventOrganizerViewModel
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventOrganizerScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onEventCreated: () -> Unit = {},
    viewModel: CreateEventOrganizerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePickerInicio by remember { mutableStateOf(false) }
    var showTimePickerFim by remember { mutableStateOf(false) }

    val fotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        viewModel.onFotosSelected(uris)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getCurrentLocation(context) { lat, lng ->
                viewModel.onLocationChange(lat, lng)
            }
        }
    }

    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let {
            viewModel.clearMessages()
            onEventCreated()
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        viewModel.onDataChange(sdf.format(Date(millis)))
                    }
                    showDatePicker = false
                }) { Text("Confirmar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePickerInicio || showTimePickerFim) {
        val timePickerState = rememberTimePickerState()
        TimePickerDialog(
            onDismissRequest = {
                showTimePickerInicio = false
                showTimePickerFim = false
            },
            confirmButton = {
                TextButton(onClick = {
                    val timeStr = String.format(Locale.getDefault(), "%02d:%02d", timePickerState.hour, timePickerState.minute)
                    if (showTimePickerInicio) viewModel.onHoraInicioChange(timeStr)
                    else viewModel.onHoraFimChange(timeStr)
                    showTimePickerInicio = false
                    showTimePickerFim = false
                }) { Text("Confirmar") }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ScreenContainer(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            verticalSpacing = 16.dp,
            padding = PaddingValues(start = 12.dp, end = 12.dp, top = 40.dp, bottom = 130.dp)
        ) {
            HomeHeader(
                state = HeaderUiState(
                    userName = uiState.userName,
                    userRole = uiState.userRole,
                    avatarUrl = uiState.userPhoto,
                    isLoggedIn = true
                ),
                onLogoClick = {},
                onAvatarClick = {},
                onNotificationClick = {}
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.PrimaryColor.copy(0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Criar Evento",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )

                    // ── SELEÇÃO DE FOTOS ───────────────────────────────────
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Black.copy(alpha = 0.5f))
                                .clickable { fotoPickerLauncher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            if (uiState.fotoUris.isNotEmpty()) {
                                AsyncImage(
                                    model = uiState.fotoUris[0],
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.Image, null, tint = Color.White.copy(0.4f), modifier = Modifier.size(48.dp))
                                    Text("Foto Principal", color = Color.White.copy(0.4f))
                                }
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            repeat(3) { index ->
                                val uri = uiState.fotoUris.getOrNull(index)
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.Black.copy(alpha = 0.5f))
                                        .clickable { fotoPickerLauncher.launch("image/*") },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (uri != null) {
                                        AsyncImage(model = uri, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                    } else {
                                        Icon(if (index == 0 && uiState.fotoUris.isEmpty() || index > 0 && index == uiState.fotoUris.size) Icons.Default.Add else Icons.Default.Image, null, tint = Color.White.copy(0.3f))
                                    }
                                }
                            }
                        }
                    }

                    // ── CAMPOS BÁSICOS ─────────────────────────────────────
                    AppTextField(
                        value = uiState.nome,
                        onValueChange = viewModel::onNomeChange,
                        placeholder = "Nome do Evento"
                    )

                    AppTextField(
                        value = uiState.descricao,
                        onValueChange = viewModel::onDescricaoChange,
                        placeholder = "Descrição do Evento",
                        modifier = Modifier.heightIn(min = 100.dp)
                    )

                    AppTextField(
                        value = uiState.local,
                        onValueChange = viewModel::onLocalChange,
                        placeholder = "Nome do Local (ex: Club, Teatro)"
                    )

                    // ── DATA E HORA ────────────────────────────────────────
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AppTextField(
                            value = uiState.data,
                            onValueChange = viewModel::onDataChange,
                            placeholder = "Data (AAAA-MM-DD)",
                            modifier = Modifier.weight(1f),
                            trailingContent = {
                                IconButton(onClick = { showDatePicker = true }) {
                                    Icon(Icons.Default.CalendarMonth, null, tint = Color.White)
                                }
                            }
                        )
                        AppTextField(
                            value = uiState.horaInicio,
                            onValueChange = viewModel::onHoraInicioChange,
                            placeholder = "Início",
                            modifier = Modifier.weight(1f),
                            trailingContent = {
                                IconButton(onClick = { showTimePickerInicio = true }) {
                                    Icon(Icons.Default.AccessTime, null, tint = Color.White)
                                }
                            }
                        )
                        AppTextField(
                            value = uiState.horaFim,
                            onValueChange = viewModel::onHoraFimChange,
                            placeholder = "Fim",
                            modifier = Modifier.weight(1f),
                            trailingContent = {
                                IconButton(onClick = { showTimePickerFim = true }) {
                                    Icon(Icons.Default.AccessTime, null, tint = Color.White)
                                }
                            }
                        )
                    }

                    // ── ENDEREÇO ───────────────────────────────────────────
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AppTextField(
                            value = uiState.cep,
                            onValueChange = viewModel::onCepChange,
                            placeholder = "CEP",
                            modifier = Modifier.weight(1f),
                            trailingContent = { if (uiState.isCepLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp), color = AppColors.colorFontLogin) }
                        )
                        IconButton(
                            onClick = {
                                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                    getCurrentLocation(context) { lat, lng -> viewModel.onLocationChange(lat, lng) }
                                } else {
                                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(containerColor = AppColors.colorFontLogin)
                        ) {
                            Icon(Icons.Default.MyLocation, "Localização Atual", tint = Color.White)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AppTextField(value = uiState.logradouro, onValueChange = {}, placeholder = "Logradouro", modifier = Modifier.weight(2f))
                        AppTextField(value = uiState.numero, onValueChange = viewModel::onNumeroChange, placeholder = "Nº", modifier = Modifier.weight(1f))
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AppTextField(value = uiState.bairro, onValueChange = {}, placeholder = "Bairro", modifier = Modifier.weight(1f))
                        AppTextField(value = uiState.cidade, onValueChange = {}, placeholder = "Cidade", modifier = Modifier.weight(1f))
                    }

                    AppTextField(value = uiState.complemento, onValueChange = viewModel::onComplementoChange, placeholder = "Complemento (Opcional)")

                    if (uiState.latitude != null) {
                        Text(
                            text = "Localização capturada: ${String.format(Locale.getDefault(), "%.4f", uiState.latitude)}, ${String.format(Locale.getDefault(), "%.4f", uiState.longitude)}",
                            color = Color.Green,
                            fontSize = 12.sp
                        )
                    }

                    // ── BOTÃO SUBMETER ─────────────────────────────────────
                    Button(
                        onClick = viewModel::createEvent,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.colorFontLogin),
                        enabled = !uiState.isLoading
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        } else {
                            Text("Marcar Evento", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }

                    uiState.errorMessage?.let {
                        Text(it, color = Color.Red, fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(context: Context, onLocationResult: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        location?.let {
            onLocationResult(it.latitude, it.longitude)
        }
    }
}

@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        text = { content() }
    )
}