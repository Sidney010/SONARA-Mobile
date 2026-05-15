package com.example.sonara.features.cadastrar.ui

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
    onSignUpSuccess: () -> Unit = {}
) {
    val uiState     = viewModel.uiState.value
    val context     = LocalContext.current
    var showOptions by remember { mutableStateOf(false) }
    var currentPhotoUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    // ── Launchers ─────────────────────────────────────────────────────────────
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) currentPhotoUri?.let { viewModel.onImagePicked(context, it) }
    }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { viewModel.onImagePicked(context, it) }
    }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            val uri = ImageUtils.createTempImageUri(context)
            currentPhotoUri = uri
            cameraLauncher.launch(uri)
        }
    }

    // ── Eventos ───────────────────────────────────────────────────────────────
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is SignUpEvent.NavigateToLogin -> onNavigateToLogin()
                is SignUpEvent.ShowError       -> snackbarHostState.showSnackbar(event.message, duration = SnackbarDuration.Short)
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { _ ->
        ScreenContainer {
            SonaraLogo()
            SignUpCard(
                // Pessoais
                nome = uiState.nome.value, nomeError = uiState.nome.error, onNomeChange = viewModel::onNomeChange,
                cpf  = uiState.cpf.value,  cpfError  = uiState.cpf.error,  onCpfChange  = viewModel::onCpfChange,
                dataNascimento      = uiState.dataNascimento.value,
                dataNascimentoError = uiState.dataNascimento.error,
                onDataNascimentoChange = viewModel::onDataNascimentoChange,
                telefone = uiState.telefone.value, onTelefoneChange = viewModel::onTelefoneChange,
                // Tipo de usuário (único)
                userType  = uiState.userType.value, userTypeError = uiState.userType.error, onUserTypeChange = viewModel::onUserTypeChange,
                // Gênero
                gender    = uiState.gender.value, genderError = uiState.gender.error, onGenderChange = viewModel::onGenderChange,
                // Nacionalidade
                nacionalidade  = uiState.nacionalidade.value,
                nacionalidadeError = uiState.nacionalidade.error,
                nacionalidades = uiState.nacionalidades,
                onNacionalidadeChange = viewModel::onNacionalidadeChange,
                // Gêneros musicais
                generosMusicaisDisponiveis = uiState.generosMusicaisDisponiveis,
                generosMusicaisSelected    = uiState.generosMusicaisSelected,
                generosMusicaisError       = uiState.generosMusicaisError,
                onGeneroMusicalToggle      = viewModel::onGeneroMusicalToggle,
                // Email/senha
                email = uiState.email.value, emailAgain = uiState.emailAgain.value,
                emailError = uiState.email.error, emailAgainError = uiState.emailAgain.error,
                onEmailChange = viewModel::onEmailChange, onEmailAgainChange = viewModel::onEmailAgainChange,
                password = uiState.password.value, passwordAgain = uiState.passwordAgain.value,
                passwordError = uiState.password.error, passwordAgainError = uiState.passwordAgain.error,
                onPasswordChange = viewModel::onPasswordChange, onPasswordAgainChange = viewModel::onPasswordAgainChange,
                // Artísticos
                nomeArtistico = uiState.nomeArtistico.value, onNomeArtisticoChange = viewModel::onNomeArtisticoChange,
                descricao     = uiState.descricao.value,     onDescricaoChange     = viewModel::onDescricaoChange,
                // Imagem
                profileImageUri   = uiState.profileImageUri,
                profileImageError = uiState.profileImageError,
                onImageClick      = { showOptions = true },
                // Endereço
                cep = uiState.address.cep, onCepChange = viewModel::onCepChange,
                rua = uiState.address.rua, onRuaChange = viewModel::onRuaChange,
                bairro = uiState.address.bairro, onBairroChange = viewModel::onBairroChange,
                cidade = uiState.address.cidade, onCidadeChange = viewModel::onCidadeChange,
                uf     = uiState.address.uf,     onUfChange     = viewModel::onUfChange,
                numero = uiState.address.numero, onNumeroChange = viewModel::onNumeroChange,
                complemento = uiState.address.complemento, onComplementoChange = viewModel::onComplementoChange,
                isLoadingCep = uiState.address.isLoading,
                // Controle
                isLoading = uiState.isLoading,
                onRegisterClick = viewModel::onRegisterClick
            )
        }
    }

    // ── Bottom sheet de seleção de foto ───────────────────────────────────────
    if (showOptions) {
        ModalBottomSheet(onDismissRequest = { showOptions = false }) {
            ListItem(
                headlineContent = { Text("Tirar foto") },
                modifier = androidx.compose.ui.Modifier.clickable {
                    showOptions = false
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            )
            ListItem(
                headlineContent = { Text("Escolher da galeria") },
                modifier = androidx.compose.ui.Modifier.clickable {
                    showOptions = false
                    galleryLauncher.launch("image/*")
                }
            )
        }
    }
}