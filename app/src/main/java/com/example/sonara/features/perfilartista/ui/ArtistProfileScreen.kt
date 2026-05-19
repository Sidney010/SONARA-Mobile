package com.example.sonara.features.perfilartista.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.features.perfilartista.components.ProfileContent
import com.example.sonara.features.perfilartista.viewmodel.ArtistProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistProfileScreen(
    viewModel: ArtistProfileViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Snackbar para mensagens de sucesso/erro
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(uiState.successMessage, uiState.errorMessage) {
        val msg = uiState.successMessage ?: uiState.errorMessage ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
        viewModel.onClearMessages()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Meu Perfil") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    // Botão Editar / Salvar
                    TextButton(
                        onClick = {
                            if (uiState.isEditing) viewModel.onSaveChanges()
                            else viewModel.onToggleEditing()
                        }
                    ) {
                        Text(
                            text  = if (uiState.isEditing) "Salvar" else "Editar",
                            color = AppColors.PrimaryOrange
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color    = AppColors.PrimaryOrange
                    )
                }

                uiState.perfil != null -> {
                    ProfileContent(
                        perfil    = uiState.perfil!!,
                        isEditing = uiState.isEditing
                    )
                }

                uiState.errorMessage != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(uiState.errorMessage!!, color = Color.Gray)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = viewModel::loadPerfil,
                            colors  = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryOrange)
                        ) { Text("Tentar novamente") }
                    }
                }
            }
        }
    }
}