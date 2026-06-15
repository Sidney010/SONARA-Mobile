package com.example.sonara.features.artista.meusEventos.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FilterListOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.components.modal.ConfirmModal
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.core.ui.theme.SonaraTheme
import com.example.sonara.domain.model.usuarioperfil.UsuarioEventoPerfil
import com.example.sonara.features.artista.meusEventos.components.EventoCardItem
import com.example.sonara.features.artista.meusEventos.viewmodel.MyEventsUiState
import com.example.sonara.features.artista.meusEventos.viewmodel.MyEventsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyEventsContent(
    uiState: MyEventsUiState,
    onStatusChange: (String) -> Unit = {},
    onDateChange: (String) -> Unit = {},
    onTimeChange: (String) -> Unit = {},
    onClearFilters: () -> Unit = {},
    onEventClick: (Int, Int?) -> Unit = { _, _ -> },
    onDeleteEvent: (UsuarioEventoPerfil) -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            onRefresh()
        }
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) {
            pullToRefreshState.endRefresh()
        }
    }

//    val filterColors = OutlinedTextFieldDefaults.colors(
//        focusedBorderColor = Color.White,
//        unfocusedBorderColor = Color.White,
//        focusedTextColor = Color.White,
//        unfocusedTextColor = Color.White,
//        focusedLabelColor = Color.White,
//        unfocusedLabelColor = Color.White,
//        cursorColor = Color.White,
//        focusedContainerColor = Color.Transparent,
//        unfocusedContainerColor = Color.Transparent
//    )

    // Picker States
//    var showDatePicker by remember { mutableStateOf(false) }
//    val datePickerState = rememberDatePickerState()
//
//    var showTimePicker by remember { mutableStateOf(false) }
//    val timePickerState = rememberTimePickerState()
//
//    if (showDatePicker) {
//        DatePickerDialog(
//            onDismissRequest = { showDatePicker = false },
//            confirmButton = {
//                TextButton(onClick = {
//                    datePickerState.selectedDateMillis?.let { millis ->
//                        val dateIso = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(millis))
//                        onDateChange(dateIso)
//                    }
//                    showDatePicker = false
//                }) {
//                    Text("OK")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showDatePicker = false }) {
//                    Text("Cancelar")
//                }
//            }
//        ) {
//            DatePicker(state = datePickerState)
//        }
//    }
//
//    if (showTimePicker) {
//        TimePickerDialog(
//            onDismissRequest = { showTimePicker = false },
//            confirmButton = {
//                TextButton(onClick = {
//                    val time = String.format(Locale.getDefault(), "%02d:%02d", timePickerState.hour, timePickerState.minute)
//                    onTimeChange(time)
//                    showTimePicker = false
//                }) {
//                    Text("OK")
//                }
//            }
//        ) {
//            TimePicker(state = timePickerState)
//        }
//    }

    ScreenContainer(
        modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection),
        verticalArrangement = Arrangement.Top,
        verticalSpacing = 16.dp,
        padding = PaddingValues(16.dp, 40.dp)
    ) {
        HomeHeader(
            state = HeaderUiState(
                userName   = uiState.userName,
                userRole   = uiState.userRole,
                avatarUrl  = uiState.userPhoto,
                isLoggedIn = uiState.isLoggedIn
            ),
            onLogoClick          = onNavigateToHome,
            onAvatarClick        = { if (uiState.isLoggedIn) onNavigateToProfile() else onNavigateToLogin() },
            onNotificationClick  = {}
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Meus Eventos",
                        color = AppColors.colorFontLogin,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

//                    IconButton(onClick = onClearFilters) {
//                        Icon(
//                            imageVector = if (uiState.statusFilter != null || uiState.dateFilter != null || uiState.timeFilter != null)
//                                Icons.Default.FilterListOff else Icons.Default.FilterList,
//                            contentDescription = "Limpar Filtros",
//                            tint = AppColors.PrimaryColor
//                        )
//                    }
                }

                // Seção de Filtros
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 12.dp),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    // Status Filter
//                    var statusExpanded by remember { mutableStateOf(false) }
//                    val statusOptions = listOf("Aprovado", "Recusado", "Pendente")
//
//                    ExposedDropdownMenuBox(
//                        expanded = statusExpanded,
//                        onExpandedChange = { statusExpanded = !statusExpanded },
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        OutlinedTextField(
//                            value = uiState.statusFilter ?: "",
//                            onValueChange = {},
//                            readOnly = true,
//                            label = { Text("Status", fontSize = 12.sp, color = Color.White) },
//                            modifier = Modifier.menuAnchor(),
//                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded) },
//                            colors = filterColors,
//                            shape = RoundedCornerShape(8.dp),
//                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp)
//                        )
//                        ExposedDropdownMenu(
//                            expanded = statusExpanded,
//                            onDismissRequest = { statusExpanded = false }
//                        ) {
//                            statusOptions.forEach { option ->
//                                DropdownMenuItem(
//                                    text = { Text(option) },
//                                    onClick = {
//                                        onStatusChange(option)
//                                        statusExpanded = false
//                                    }
//                                )
//                            }
//                        }
//                    }
//
//                    // Date Filter
//                    Box(modifier = Modifier.weight(1f)) {
//                        val displayDate = remember(uiState.dateFilter) {
//                            if (uiState.dateFilter != null) {
//                                try {
//                                    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//                                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//                                    parser.parse(uiState.dateFilter)?.let { formatter.format(it) } ?: uiState.dateFilter
//                                } catch (e: Exception) {
//                                    uiState.dateFilter
//                                }
//                            } else ""
//                        }
//                        OutlinedTextField(
//                            value = displayDate,
//                            onValueChange = {},
//                            readOnly = true,
//                            label = { Text("Data", fontSize = 12.sp, color = Color.White) },
//                            modifier = Modifier.fillMaxWidth(),
//                            colors = filterColors,
//                            shape = RoundedCornerShape(8.dp),
//                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp)
//                        )
//                        Box(
//                            modifier = Modifier
//                                .matchParentSize()
//                                .clickable { showDatePicker = true }
//                        )
//                    }
//
//                    // Time Filter
//                    Box(modifier = Modifier.weight(1f)) {
//                        OutlinedTextField(
//                            value = uiState.timeFilter ?: "",
//                            onValueChange = {},
//                            readOnly = true,
//                            label = { Text("Horário", fontSize = 12.sp, color = Color.White) },
//                            modifier = Modifier.fillMaxWidth(),
//                            colors = filterColors,
//                            shape = RoundedCornerShape(8.dp),
//                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp)
//                        )
//                        Box(
//                            modifier = Modifier
//                                .matchParentSize()
//                                .clickable { showTimePicker = true }
//                        )
//                    }
//                }

                if (uiState.isLoading && !uiState.isRefreshing) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AppColors.colorFontLogin)
                    }
                } else if (uiState.filteredEventos.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (uiState.allEventos.isEmpty()) "Você ainda não se candidatou a nenhum evento."
                            else "Nenhum evento corresponde aos filtros.",
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(uiState.filteredEventos) { evento ->
                            EventoCardItem(
                                evento = evento,
                                onClick = { onEventClick(evento.idEvento, evento.idEventoArtista) },
                                onDelete = { onDeleteEvent(evento) }
                            )
                        }
                    }
                }
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = AppColors.colorFontLogin,
                containerColor = Color.Transparent
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyEvents(
    modifier: Modifier = Modifier,
    viewModel: MyEventsViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onEventClick: (Int, Int?) -> Unit = { _, _ -> }
) {
    val uiState by viewModel.uiState.collectAsState()
    var eventToDelete by remember { mutableStateOf<UsuarioEventoPerfil?>(null) }

    if (eventToDelete != null) {
        ConfirmModal(
            title = "Cancelar Candidatura",
            message = "Deseja realmente cancelar sua candidatura para o evento ${eventToDelete?.eventoNome}?",
            confirmText = "Sim, cancelar",
            cancelText = "Não",
            onConfirm = {
                eventToDelete?.let { event ->
                    event.idEventoArtista?.let { id ->
                        viewModel.cancelCandidacy(id, event.status)
                    }
                }
                eventToDelete = null
            },
            onDismiss = { eventToDelete = null }
        )
    }

    MyEventsContent(
        uiState = uiState,
        onNavigateToLogin = onNavigateToLogin,
        onNavigateToProfile = onNavigateToProfile,
        onNavigateToHome = onNavigateToHome,
        onStatusChange = { viewModel.setStatusFilter(it.ifBlank { null }) },
        onDateChange = { viewModel.setDateFilter(it.ifBlank { null }) },
        onTimeChange = { viewModel.setTimeFilter(it.ifBlank { null }) },
        onClearFilters = { viewModel.clearFilters() },
        onEventClick = onEventClick,
        onDeleteEvent = { eventToDelete = it },
        isRefreshing = uiState.isRefreshing,
        onRefresh = { uiState.userId?.let { viewModel.loadEventos(it, isRefreshing = true) } }
    )
}

//@Composable
//fun TimePickerDialog(
//    onDismissRequest: () -> Unit,
//    confirmButton: @Composable () -> Unit,
//    content: @Composable () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = onDismissRequest,
//        confirmButton = confirmButton,
//        text = { content() }
//    )
//}
//
//@Preview(showBackground = true, backgroundColor = 0xFF000000)
//@Composable
//fun MyEventsPreview() {
//    SonaraTheme {
//        MyEventsContent(
//            uiState = MyEventsUiState(
//                userName = "John Doe",
//                userRole = "ARTISTA",
//                isLoggedIn = true,
//                allEventos = listOf(
//                    UsuarioEventoPerfil(
//                        idEvento = 1,
//                        eventoNome = "Evento 1",
//                        status = "Pendente",
//                        eventoData = "2023-10-01",
//                        horaInicio = "20:00",
//                        cache = null,
//                        endereco = null,
//                        horaFim = null,
//                        descricao = null,
//                        sobreArtista = null,
//                        motivoInscricao = null
//                    ),
//                    UsuarioEventoPerfil(
//                        idEvento = 2,
//                        eventoNome = "Evento 2",
//                        status = "Confirmado",
//                        eventoData = "2023-10-02",
//                        horaInicio = "21:00",
//                        cache = null,
//                        endereco = null,
//                        horaFim = null,
//                        descricao = null,
//                        sobreArtista = null,
//                        motivoInscricao = null
//                    )
//                ),
//                filteredEventos = listOf(
//                    UsuarioEventoPerfil(
//                        idEvento = 1,
//                        eventoNome = "Evento 1",
//                        status = "Pendente",
//                        eventoData = "2023-10-01",
//                        horaInicio = "20:00",
//                        cache = null,
//                        endereco = null,
//                        horaFim = null,
//                        descricao = null,
//                        sobreArtista = null,
//                        motivoInscricao = null
//                    ),
//                    UsuarioEventoPerfil(
//                        idEvento = 2,
//                        eventoNome = "Evento 2",
//                        status = "Confirmado",
//                        eventoData = "2023-10-02",
//                        horaInicio = "21:00",
//                        cache = null,
//                        endereco = null,
//                        horaFim = null,
//                        descricao = null,
//                        sobreArtista = null,
//                        motivoInscricao = null
//                    )
//                ),
//                timeFilter = null
//            )
//        )
//    }
//}
