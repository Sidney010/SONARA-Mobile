package com.example.sonara.features.cadastrar.ui

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.features.cadastrar.components.SignUpCard
import com.example.sonara.features.cadastrar.viewmodel.SignUpViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel()
) {
    val uiState by viewModel.uiState
    val context = LocalContext.current

    var showImageOptions by remember { mutableStateOf(false) }

    // URI dinâmico (correção real do bug)
    var cameraUri by remember { mutableStateOf<Uri?>(null) }

    // CAMERA
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            cameraUri?.let { viewModel.onProfileImageSelected(it) }
        }
    }

    // GALERIA
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.onProfileImageSelected(uri)
    }

    // PERMISSÃO
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted && cameraUri != null) {
            cameraLauncher.launch(cameraUri!!)
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

    // BottomSheet
    if (showImageOptions) {
        ModalBottomSheet(
            onDismissRequest = { showImageOptions = false }
        ) {

            ListItem(
                headlineContent = { Text("Tirar foto") },
                modifier = Modifier.clickable {
                    showImageOptions = false

                    val newUri = createTempImageUri(context)
                    cameraUri = newUri

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

fun createTempImageUri(context: Context): Uri {
    val file = File.createTempFile("profile_", ".jpg", context.cacheDir)
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}