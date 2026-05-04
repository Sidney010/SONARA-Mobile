package com.example.sonara.features.cadastrar.ui

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sonara.core.image.ImageUtils
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.features.cadastrar.components.SignUpCard
import com.example.sonara.features.cadastrar.event.SignUpEvent
import com.example.sonara.features.cadastrar.viewmodel.SignUpViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
) {
    val uiState by viewModel.uiState
    val context = LocalContext.current

    var showImageOptions by remember { mutableStateOf(false) }

    // Guardar a URI da foto que está sendo tirada no momento
    var currentPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            currentPhotoUri?.let { viewModel.onImagePicked(context, it) }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.onImagePicked(context, it) }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = ImageUtils.createTempImageUri(context)
            currentPhotoUri = uri
            cameraLauncher.launch(uri)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {

                is SignUpEvent.NavigateToLogin -> {
                    onNavigateToLogin
                }

                is SignUpEvent.ShowError -> {
                    // mostrar snackbar futuramente
                }

            }
        }
    }

    ScreenContainer {
        SonaraLogo()

        SignUpCard(
            nome = uiState.nome.value,
            nomeError = uiState.nome.error,
            onNomeChange = viewModel::onNomeChange,

            cpf = uiState.cpf.value,
            cpfError = uiState.cpf.error,
            onCpfChange = viewModel::onCpfChange,

            email = uiState.email.value,
            emailAgain = uiState.emailAgain.value,
            emailError = uiState.email.error,
            emailAgainError = uiState.emailAgain.error,
            onEmailChange = viewModel::onEmailChange,
            onEmailAgainChange = viewModel::onEmailAgainChange,

            password = uiState.password.value,
            passwordAgain = uiState.passwordAgain.value,
            passwordError = uiState.password.error,
            passwordAgainError = uiState.passwordAgain.error,
            onPasswordChange = viewModel::onPasswordChange,
            onPasswordAgainChange = viewModel::onPasswordAgainChange,

            userType = uiState.userType.value,
            onUserTypeChange = viewModel::onUserTypeChange,

            profileImageUri = uiState.profileImageUri,
            profileImageError = uiState.profileImageError,

            onImageClick = { showImageOptions = true },
            onRegisterClick = viewModel::onRegisterClick
        )
    }

    if (showImageOptions) {
        ModalBottomSheet(onDismissRequest = { showImageOptions = false }) {
            ListItem(
                headlineContent = { Text("Tirar foto") },
                modifier = Modifier.clickable {
                    showImageOptions = false
                    // Sempre pedir/verificar permissão antes de criar a URI
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            )
            ListItem(
                headlineContent = { Text("Escolher da galeria") },
                modifier = Modifier.clickable {
                    showImageOptions = false
                    galleryLauncher.launch("image/*")
                }
            )
        }
    }
}