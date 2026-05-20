package com.example.sonara.features.cadastrar.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.core.form.FieldState
import com.example.sonara.core.storage.FormData
import com.example.sonara.core.validation.CpfValidator
import com.example.sonara.core.validation.EmailValidator
import com.example.sonara.core.validation.NomeValidator
import com.example.sonara.core.validation.PasswordValidator
import com.example.sonara.core.validation.getErrorOrNull
import com.example.sonara.domain.model.Gender
import com.example.sonara.domain.model.Nacionalidade
import com.example.sonara.domain.model.UserType
import com.example.sonara.domain.model.Usuario
import com.example.sonara.domain.usecase.BuscarEnderecoPorCepUseCase
import com.example.sonara.domain.usecase.ClearFormUseCase
import com.example.sonara.domain.usecase.GetFormUseCase
import com.example.sonara.domain.usecase.ListarGenerosMusicaisUseCase
import com.example.sonara.domain.usecase.ListarNacionalidadesUseCase
import com.example.sonara.domain.usecase.ProcessImageUseCase
import com.example.sonara.domain.usecase.RegisterUserUseCase
import com.example.sonara.domain.usecase.SaveFormUseCase
import com.example.sonara.features.cadastrar.event.SignUpEvent
import com.example.sonara.features.cadastrar.form.AddressFormManager
import com.example.sonara.features.cadastrar.model.SignUpUIState
import com.example.sonara.features.cadastrar.validation.CepValidator
import com.example.sonara.features.cadastrar.validation.GenderValidator
import com.example.sonara.features.cadastrar.validation.UserTypeValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registerUserUseCase: RegisterUserUseCase,
    private val buscarEnderecoPorCepUseCase: BuscarEnderecoPorCepUseCase,
    private val listarNacionalidadesUseCase: ListarNacionalidadesUseCase,
    private val listarGenerosMusicaisUseCase: ListarGenerosMusicaisUseCase,
    private val processImageUseCase: ProcessImageUseCase,
    private val saveFormUseCase: SaveFormUseCase,
    private val getFormUseCase: GetFormUseCase,
    private val clearFormUseCase: ClearFormUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = mutableStateOf(SignUpUIState())
    val uiState: State<SignUpUIState> get() = _uiState

    private val _event = MutableSharedFlow<SignUpEvent>()
    val event = _event.asSharedFlow()

    private val addressManager = AddressFormManager()
    private var cepJob: Job? = null

    init {
        loadCatalogs()
        restoreForm()
    }

    // ── Catálogos ──────────────────────────────────────────────────────

    private fun loadCatalogs() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingCatalogs = true)
            val nacs = (listarNacionalidadesUseCase() as? AppResult.Success)?.data ?: emptyList()
            val gens = (listarGenerosMusicaisUseCase() as? AppResult.Success)?.data ?: emptyList()
            _uiState.value = _uiState.value.copy(
                nacionalidades            = nacs,
                generosMusicaisDisponiveis = gens,
                isLoadingCatalogs         = false
            )
        }
    }

    // ── Restauração do formulário ──────────────────────────────────────

    private fun restoreForm() {
        viewModelScope.launch {
            getFormUseCase().collect { form ->
                if (_uiState.value.nome.value.isBlank() && form.name.isNotBlank()) {
                    _uiState.value = _uiState.value.copy(
                        nome          = FieldState(form.name),
                        email         = FieldState(form.email),
                        cpf           = FieldState(form.cpf),
                        dataNascimento = FieldState(form.dataNasc),
                        telefone      = FieldState(form.telefone)
                    )
                }
            }
        }
    }

    // ── Handlers de campo ──────────────────────────────────────────────

    fun onNomeChange(v: String) {
        _uiState.value = _uiState.value.copy(
            nome = _uiState.value.nome.copy(
                value = v,
                error = NomeValidator.validate(v).getErrorOrNull()
            )
        )
        scheduleSaveForm()
    }

    fun onCpfChange(v: String) {
        _uiState.value = _uiState.value.copy(
            cpf = _uiState.value.cpf.copy(
                value = v,
                error = CpfValidator.validate(v).getErrorOrNull()
            )
        )
        scheduleSaveForm()
    }

    fun onDataNascimentoChange(v: String) {
        _uiState.value = _uiState.value.copy(
            dataNascimento = _uiState.value.dataNascimento.copy(value = v)
        )
    }

    /** Armazena apenas dígitos (máx 11); a máscara é aplicada pelo VisualTransformation na UI. */
    fun onTelefoneChange(v: String) {
        val digits = v.filter { it.isDigit() }.take(11)
        _uiState.value = _uiState.value.copy(
            telefone = _uiState.value.telefone.copy(value = digits)
        )
    }

    fun onEmailChange(v: String) {
        _uiState.value = _uiState.value.copy(
            email = _uiState.value.email.copy(
                value = v,
                error = EmailValidator.validate(v).getErrorOrNull()
            )
        )
    }

    fun onEmailAgainChange(v: String) {
        val error = if (v != _uiState.value.email.value) "Emails não coincidem" else null
        _uiState.value = _uiState.value.copy(
            emailAgain = _uiState.value.emailAgain.copy(value = v, error = error)
        )
    }

    fun onPasswordChange(v: String) {
        _uiState.value = _uiState.value.copy(
            password = _uiState.value.password.copy(
                value = v,
                error = PasswordValidator.validate(v).getErrorOrNull()
            )
        )
    }

    fun onPasswordAgainChange(v: String) {
        val error = if (v != _uiState.value.password.value) "Senhas não coincidem" else null
        _uiState.value = _uiState.value.copy(
            passwordAgain = _uiState.value.passwordAgain.copy(value = v, error = error)
        )
    }

    fun onUserTypeChange(type: UserType) {
        _uiState.value = _uiState.value.copy(
            userType = _uiState.value.userType.copy(value = type, error = null)
        )
    }

    fun onGenderChange(gender: Gender) {
        _uiState.value = _uiState.value.copy(
            gender = _uiState.value.gender.copy(value = gender, error = null)
        )
    }

    fun onNacionalidadeChange(nac: Nacionalidade) {
        _uiState.value = _uiState.value.copy(
            nacionalidade = _uiState.value.nacionalidade.copy(value = nac)
        )
    }

    fun onGeneroMusicalToggle(id: Int) {
        val current = _uiState.value.generosMusicaisSelected
        _uiState.value = _uiState.value.copy(
            generosMusicaisSelected = if (id in current) current - id else current + id,
            generosMusicaisError    = null
        )
    }

    fun onNomeArtisticoChange(v: String) {
        _uiState.value = _uiState.value.copy(
            nomeArtistico = _uiState.value.nomeArtistico.copy(value = v)
        )
    }

    fun onDescricaoChange(v: String) {
        _uiState.value = _uiState.value.copy(
            descricao = _uiState.value.descricao.copy(value = v)
        )
    }

    // ── Endereço ────────────────────────────────────────────────────────

    fun onCepChange(v: String) {
        val newAddr = addressManager.updateCep(_uiState.value.address, v)
        _uiState.value = _uiState.value.copy(address = newAddr)
        if (newAddr.cep.length == 8) {
            cepJob?.cancel()
            cepJob = viewModelScope.launch {
                delay(300)
                buscarCep(newAddr.cep)
            }
        }
    }

    fun onRuaChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = addressManager.updateRua(_uiState.value.address, v)
        )
    }

    fun onBairroChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = addressManager.updateBairro(_uiState.value.address, v)
        )
    }

    fun onCidadeChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = addressManager.updateCidade(_uiState.value.address, v)
        )
    }

    fun onUfChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = addressManager.updateUf(_uiState.value.address, v)
        )
    }

    fun onNumeroChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address.copy(numero = v)
        )
    }

    fun onComplementoChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address.copy(complemento = v)
        )
    }

    private suspend fun buscarCep(cep: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address.copy(isLoading = true)
        )
        when (val r = buscarEnderecoPorCepUseCase(cep)) {
            is AppResult.Success -> _uiState.value = _uiState.value.copy(
                address = _uiState.value.address.copy(
                    rua     = r.data.rua,
                    bairro  = r.data.bairro,
                    cidade  = r.data.cidade,
                    uf      = r.data.uf,
                    isLoading = false
                )
            )
            is AppResult.Error   -> _uiState.value = _uiState.value.copy(
                address = _uiState.value.address.copy(isLoading = false)
            )
        }
    }

    // ── Imagem ──────────────────────────────────────────────────────────

    fun onImagePicked(context: Context, uri: Uri) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isImageLoading = true)
            when (val r = processImageUseCase(context, uri)) {
                is AppResult.Success -> _uiState.value = _uiState.value.copy(
                    profileImageUri   = r.data,
                    profileImageError = null,
                    isImageLoading    = false
                )
                is AppResult.Error   -> _uiState.value = _uiState.value.copy(
                    profileImageError = "Erro ao processar imagem",
                    isImageLoading    = false
                )
            }
        }
    }

    // ── Cadastro ────────────────────────────────────────────────────────

    fun onRegisterClick() {
        val state     = _uiState.value
        val isArtista = state.userType.value == UserType.ARTISTA

        // ── Validações
        val nomeError          = NomeValidator.validate(state.nome.value).getErrorOrNull()
        val emailError         = EmailValidator.validate(state.email.value).getErrorOrNull()
        val emailAgainError    = if (state.emailAgain.value != state.email.value) "Emails não coincidem" else null
        val passwordError      = PasswordValidator.validate(state.password.value).getErrorOrNull()
        val passwordAgainError = if (state.passwordAgain.value != state.password.value) "Senhas não coincidem" else null
        val cpfError           = CpfValidator.validate(state.cpf.value).getErrorOrNull()
        val userTypeError      = UserTypeValidator.validate(state.userType.value).getErrorOrNull()
        val genderError        = GenderValidator.validate(state.gender.value).getErrorOrNull()
        val cepError           = CepValidator.validate(state.address.cep)
        val generosMusicaisError = if (isArtista && state.generosMusicaisSelected.isEmpty())
            "Selecione pelo menos um gênero musical" else null

        _uiState.value = state.copy(
            nome              = state.nome.copy(error = nomeError),
            email             = state.email.copy(error = emailError),
            emailAgain        = state.emailAgain.copy(error = emailAgainError),
            password          = state.password.copy(error = passwordError),
            passwordAgain     = state.passwordAgain.copy(error = passwordAgainError),
            cpf               = state.cpf.copy(error = cpfError),
            userType          = state.userType.copy(error = userTypeError),
            gender            = state.gender.copy(error = genderError),
            address           = state.address.copy(cepError = cepError),
            generosMusicaisError = generosMusicaisError
        )

        val hasErrors = listOf(
            nomeError, emailError, emailAgainError, passwordError,
            passwordAgainError, cpfError, userTypeError, genderError,
            cepError, generosMusicaisError
        ).any { it != null }

        if (hasErrors) return

        // ── Monta domínio
        val usuario = Usuario(
            nome            = state.nome.value.trim(),
            email           = state.email.value.trim(),
            senha           = state.password.value,
            cpf             = state.cpf.value,
            dataNascimento  = state.dataNascimento.value,
            nacionalidadeId = state.nacionalidade.value?.id,
            generoId        = state.gender.value?.apiId,
            telefone        = state.telefone.value.filter { it.isDigit() },
            tipoUsuario     = state.userType.value?.apiValue ?: "usuario",
            // Campos de artista — só enviados se tipo == ARTISTA
            nomeArtistico   = if (isArtista) state.nomeArtistico.value.ifBlank { null } else null,
            descricao       = if (isArtista) state.descricao.value.ifBlank { null } else null,
            generosMusicais = if (isArtista) state.generosMusicaisSelected.toList() else emptyList(),
            cep             = state.address.cep,
            cidade          = state.address.cidade,
            estado          = state.address.uf,
            logradouro      = state.address.rua,
            numero          = state.address.numero.ifBlank { null },
            complemento     = state.address.complemento.ifBlank { null },
            bairro          = state.address.bairro
        )

        val photoFilePath = state.profileImageUri?.path

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            when (val result = registerUserUseCase(usuario, photoFilePath)) {
                is AppResult.Success -> {
                    clearFormUseCase()
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _event.emit(SignUpEvent.NavigateToLogin)
                }
                is AppResult.Error   -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _event.emit(
                        SignUpEvent.ShowError(
                            result.exception.message ?: "Erro ao cadastrar"
                        )
                    )
                }
            }
        }
    }

    // ── Persistência do formulário ──────────────────────────────────────

    private var saveJob: Job? = null
    private fun scheduleSaveForm() {
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            delay(500)
            val s = _uiState.value
            saveFormUseCase(
                FormData(
                    name          = s.nome.value,
                    email         = s.email.value,
                    cpf           = s.cpf.value,
                    password      = s.password.value,
                    dataNasc      = s.dataNascimento.value,
                    telefone      = s.telefone.value,
                    userType      = s.userType.value?.apiValue ?: "",
                    nomeArtistico = s.nomeArtistico.value,
                    descricao     = s.descricao.value,
                    nacionalidadeId = s.nacionalidade.value?.id?.toString() ?: "",
                    generoId      = s.gender.value?.apiId?.toString() ?: "",
                    generosMusicais = s.generosMusicaisSelected.joinToString(","),
                    cep           = s.address.cep,
                    rua           = s.address.rua,
                    bairro        = s.address.bairro,
                    cidade        = s.address.cidade,
                    uf            = s.address.uf,
                    numero        = s.address.numero,
                    complemento   = s.address.complemento
                )
            )
        }
    }
}