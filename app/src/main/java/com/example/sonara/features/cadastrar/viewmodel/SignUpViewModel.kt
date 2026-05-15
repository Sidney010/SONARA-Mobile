package com.example.sonara.features.cadastrar.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.common.AppResult
import com.example.sonara.core.storage.FormData
import com.example.sonara.core.validation.*
import com.example.sonara.domain.model.Gender
import com.example.sonara.domain.model.GeneroMusical
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
import com.example.sonara.features.cadastrar.model.SignUpUIState
import com.example.sonara.features.cadastrar.validation.GenderValidator
import com.example.sonara.features.cadastrar.validation.UserTypeValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val processImageUseCase: ProcessImageUseCase,
    private val saveFormUseCase: SaveFormUseCase,
    private val getFormUseCase: GetFormUseCase,
    private val clearFormUseCase: ClearFormUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val buscarEnderecoPorCepUseCase: BuscarEnderecoPorCepUseCase,
    private val listarNacionalidadesUseCase: ListarNacionalidadesUseCase,
    private val listarGenerosMusicaisUseCase: ListarGenerosMusicaisUseCase
) : ViewModel() {

    private var cepJob: Job?  = null
    private var saveJob: Job? = null

    private val _uiState = mutableStateOf(SignUpUIState())
    val uiState: State<SignUpUIState> = _uiState

    private val _event = MutableSharedFlow<SignUpEvent>()
    val event = _event.asSharedFlow()

    init {
        loadCatalogs()
        restoreForm()
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Catálogos
    // ─────────────────────────────────────────────────────────────────────────

    private fun loadCatalogs() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingCatalogs = true)

            // Carrega em paralelo
            val nacionalidadesJob = launch {
                when (val result = listarNacionalidadesUseCase()) {
                    is AppResult.Success -> _uiState.value = _uiState.value.copy(
                        nacionalidades = result.data
                    )
                    is AppResult.Error  -> { /* silencia — usuário pode tentar novamente */ }
                }
            }
            val generosMusicaisJob = launch {
                when (val result = listarGenerosMusicaisUseCase()) {
                    is AppResult.Success -> _uiState.value = _uiState.value.copy(
                        generosMusicaisDisponiveis = result.data
                            .distinctBy { it.id } // remove duplicatas do banco
                            .sortedBy { it.nome }
                    )
                    is AppResult.Error  -> { }
                }
            }

            nacionalidadesJob.join()
            generosMusicaisJob.join()
            _uiState.value = _uiState.value.copy(isLoadingCatalogs = false)
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Restaurar formulário do DataStore
    // ─────────────────────────────────────────────────────────────────────────

    private fun restoreForm() {
        viewModelScope.launch {
            getFormUseCase().collect { data ->
                _uiState.value = _uiState.value.copy(
                    nome           = _uiState.value.nome.copy(value = data.name),
                    email          = _uiState.value.email.copy(value = data.email),
                    cpf            = _uiState.value.cpf.copy(value = data.cpf),
                    password       = _uiState.value.password.copy(value = data.password),
                    dataNascimento = _uiState.value.dataNascimento.copy(value = data.dataNasc),
                    telefone       = _uiState.value.telefone.copy(value = data.telefone),
                    nomeArtistico  = _uiState.value.nomeArtistico.copy(value = data.nomeArtistico),
                    descricao      = _uiState.value.descricao.copy(value = data.descricao),
                    profileImageUri= data.image?.let { Uri.parse(it) },
                    userType       = _uiState.value.userType.copy(
                        value = data.userType
                            .takeIf { it.isNotBlank() }
                            ?.let { runCatching { UserType.valueOf(it) }.getOrNull() }
                    ),
                    generosMusicaisSelected = data.generosMusicais
                        .split(",")
                        .mapNotNull { it.trim().toIntOrNull() }
                        .toSet(),
                    address = _uiState.value.address.copy(
                        cep         = data.cep,
                        rua         = data.rua,
                        bairro      = data.bairro,
                        cidade      = data.cidade,
                        uf          = data.uf,
                        numero      = data.numero,
                        complemento = data.complemento
                    )
                )
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Auto-save com debounce
    // ─────────────────────────────────────────────────────────────────────────

    private fun saveWithDelay() {
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            delay(500)
            val s = _uiState.value
            saveFormUseCase(
                FormData(
                    name            = s.nome.value,
                    email           = s.email.value,
                    cpf             = s.cpf.value,
                    password        = s.password.value,
                    image           = s.profileImageUri?.toString(),
                    dataNasc        = s.dataNascimento.value,
                    telefone        = s.telefone.value,
                    userType        = s.userType.value?.name ?: "",
                    nomeArtistico   = s.nomeArtistico.value,
                    descricao       = s.descricao.value,
                    nacionalidadeId = s.nacionalidade.value?.id?.toString() ?: "",
                    generosMusicais = s.generosMusicaisSelected.joinToString(","),
                    cep             = s.address.cep,
                    rua             = s.address.rua,
                    bairro          = s.address.bairro,
                    cidade          = s.address.cidade,
                    uf              = s.address.uf,
                    numero          = s.address.numero,
                    complemento     = s.address.complemento
                )
            )
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Handlers de campos
    // ─────────────────────────────────────────────────────────────────────────

    fun onNomeChange(value: String) {
        _uiState.value = _uiState.value.copy(
            nome = _uiState.value.nome.copy(
                value = value,
                error = NomeValidator.validate(value).getErrorOrNull()
            )
        )
        saveWithDelay()
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(
            email = _uiState.value.email.copy(
                value = value,
                error = EmailValidator.validate(value).getErrorOrNull()
            )
        )
        saveWithDelay()
    }

    fun onEmailAgainChange(value: String) {
        val error = if (value != _uiState.value.email.value) "Emails não conferem" else null
        _uiState.value = _uiState.value.copy(
            emailAgain = _uiState.value.emailAgain.copy(value = value, error = error)
        )
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(
            password = _uiState.value.password.copy(
                value = value,
                error = PasswordValidator.validate(value).getErrorOrNull(),
                isTouched = true
            )
        )
        saveWithDelay()
    }

    fun onPasswordAgainChange(value: String) {
        val error = if (value != _uiState.value.password.value) "Senhas não conferem" else null
        _uiState.value = _uiState.value.copy(
            passwordAgain = _uiState.value.passwordAgain.copy(
                value = value,
                error = error,
                isTouched = true
            )
        )
    }

    fun onCpfChange(value: String) {
        val digits = value.filter { it.isDigit() }.take(11)
        _uiState.value = _uiState.value.copy(
            cpf = _uiState.value.cpf.copy(
                value = digits,
                error = CpfValidator.validate(digits).getErrorOrNull()
            )
        )
        saveWithDelay()
    }

    fun onDataNascimentoChange(value: String) {
        _uiState.value = _uiState.value.copy(
            dataNascimento = _uiState.value.dataNascimento.copy(
                value = value,
                error = if (value.isBlank()) "Data de nascimento obrigatória" else null
            )
        )
        saveWithDelay()
    }

    fun onTelefoneChange(value: String) {
        _uiState.value = _uiState.value.copy(
            telefone = _uiState.value.telefone.copy(value = value)
        )
        saveWithDelay()
    }

    fun onGenderChange(gender: Gender) {
        _uiState.value = _uiState.value.copy(
            gender = _uiState.value.gender.copy(value = gender, error = null, isTouched = true)
        )
        saveWithDelay()
    }

    // CORREÇÃO: UserType agora é seleção única
    fun onUserTypeChange(type: UserType) {
        _uiState.value = _uiState.value.copy(
            userType = _uiState.value.userType.copy(value = type, error = null, isTouched = true)
        )
        saveWithDelay()
    }

    fun onNacionalidadeChange(nac: Nacionalidade) {
        _uiState.value = _uiState.value.copy(
            nacionalidade = _uiState.value.nacionalidade.copy(value = nac, error = null)
        )
        saveWithDelay()
    }

    fun onGeneroMusicalToggle(id: Int) {
        val current = _uiState.value.generosMusicaisSelected
        val updated = if (id in current) current - id else current + id
        _uiState.value = _uiState.value.copy(
            generosMusicaisSelected = updated,
            generosMusicaisError    = if (updated.isEmpty()) "Selecione ao menos um gênero musical" else null
        )
        saveWithDelay()
    }

    fun onNomeArtisticoChange(value: String) {
        _uiState.value = _uiState.value.copy(
            nomeArtistico = _uiState.value.nomeArtistico.copy(value = value)
        )
        saveWithDelay()
    }

    fun onDescricaoChange(value: String) {
        _uiState.value = _uiState.value.copy(
            descricao = _uiState.value.descricao.copy(value = value)
        )
        saveWithDelay()
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Endereço — CEP com auto-preenchimento real
    // ─────────────────────────────────────────────────────────────────────────

    fun onCepChange(value: String) {
        val digits = value.filter { it.isDigit() }.take(8)
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address.copy(cep = digits, cepError = null)
        )
        saveWithDelay()

        // Busca automática ao completar 8 dígitos
        if (digits.length == 8) {
            buscarCep(digits)
        }
    }

    private fun buscarCep(cep: String) {
        cepJob?.cancel()
        cepJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                address = _uiState.value.address.copy(isLoading = true, cepError = null)
            )
            when (val result = buscarEnderecoPorCepUseCase(cep)) {
                is AppResult.Success -> {
                    val e = result.data
                    _uiState.value = _uiState.value.copy(
                        address = _uiState.value.address.copy(
                            rua     = e.rua,
                            bairro  = e.bairro,
                            cidade  = e.cidade,
                            uf      = e.uf,
                            cepError= null,
                            isLoading = false
                        )
                    )
                    saveWithDelay()
                }
                is AppResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        address = _uiState.value.address.copy(
                            cepError  = "CEP não encontrado",
                            isLoading = false
                        )
                    )
                }
            }
        }
    }

    fun onRuaChange(value: String) {
        _uiState.value = _uiState.value.copy(address = _uiState.value.address.copy(rua = value))
        saveWithDelay()
    }

    fun onBairroChange(value: String) {
        _uiState.value = _uiState.value.copy(address = _uiState.value.address.copy(bairro = value))
        saveWithDelay()
    }

    fun onCidadeChange(value: String) {
        _uiState.value = _uiState.value.copy(address = _uiState.value.address.copy(cidade = value))
        saveWithDelay()
    }

    fun onUfChange(value: String) {
        _uiState.value = _uiState.value.copy(address = _uiState.value.address.copy(uf = value.take(2)))
        saveWithDelay()
    }

    fun onNumeroChange(value: String) {
        _uiState.value = _uiState.value.copy(address = _uiState.value.address.copy(numero = value))
        saveWithDelay()
    }

    fun onComplementoChange(value: String) {
        _uiState.value = _uiState.value.copy(address = _uiState.value.address.copy(complemento = value))
        saveWithDelay()
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Imagem de perfil
    // ─────────────────────────────────────────────────────────────────────────

    fun onImagePicked(context: Context, uri: Uri?) {
        if (uri == null) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isImageLoading = true)
            val result  = processImageUseCase(context, uri)
            val finalUri = when (result) {
                is AppResult.Success -> result.data
                is AppResult.Error   -> uri
            }
            _uiState.value = _uiState.value.copy(
                profileImageUri  = finalUri,
                profileImageError = null,
                isImageLoading   = false
            )
            saveWithDelay()
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Validação geral e cadastro
    // ─────────────────────────────────────────────────────────────────────────

    private fun validateAll(): Boolean {
        val s = _uiState.value

        val nomeError       = NomeValidator.validate(s.nome.value).getErrorOrNull()
        val emailError      = EmailValidator.validate(s.email.value).getErrorOrNull()
        val emailAgainError = if (s.emailAgain.value != s.email.value) "Emails não conferem" else null
        val cpfError        = CpfValidator.validate(s.cpf.value).getErrorOrNull()
        val passwordError   = PasswordValidator.validate(s.password.value).getErrorOrNull()
        val passwordAgainError = if (s.passwordAgain.value != s.password.value) "Senhas não conferem" else null
        val userTypeError   = UserTypeValidator.validate(s.userType.value).getErrorOrNull()
        val genderError     = GenderValidator.validate(s.gender.value).getErrorOrNull()
        val dataNascError   = if (s.dataNascimento.value.isBlank()) "Data de nascimento obrigatória" else null
        val genMusicaisError= if (s.generosMusicaisSelected.isEmpty()) "Selecione ao menos um gênero musical" else null
        val nacionalidadeError = if (s.nacionalidade.value == null) "Selecione uma nacionalidade" else null

        _uiState.value = s.copy(
            nome           = s.nome.copy(error = nomeError),
            email          = s.email.copy(error = emailError),
            emailAgain     = s.emailAgain.copy(error = emailAgainError),
            cpf            = s.cpf.copy(error = cpfError),
            password       = s.password.copy(error = passwordError),
            passwordAgain  = s.passwordAgain.copy(error = passwordAgainError),
            userType       = s.userType.copy(error = userTypeError),
            gender         = s.gender.copy(error = genderError),
            dataNascimento = s.dataNascimento.copy(error = dataNascError),
            generosMusicaisError = genMusicaisError,
            nacionalidade  = s.nacionalidade.copy(error = nacionalidadeError)
        )

        return listOf(
            nomeError, emailError, emailAgainError, cpfError,
            passwordError, passwordAgainError, userTypeError,
            genderError, dataNascError, genMusicaisError, nacionalidadeError
        ).all { it == null }
    }

    fun onRegisterClick() {
        if (!validateAll()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val s = _uiState.value

            val user = Usuario(
                nome           = s.nome.value,
                email          = s.email.value,
                senha          = s.password.value,
                cpf            = s.cpf.value,
                dataNascimento = s.dataNascimento.value,
                telefone       = s.telefone.value.takeIf { it.isNotBlank() },
                tipoUsuario    = s.userType.value?.apiValue ?: "usuario",
                generoId       = s.gender.value?.apiId,
                nacionalidadeId= s.nacionalidade.value?.id,
                nomeArtistico  = s.nomeArtistico.value.takeIf { it.isNotBlank() },
                descricao      = s.descricao.value.takeIf { it.isNotBlank() },
                cep            = s.address.cep,
                logradouro     = s.address.rua,
                bairro         = s.address.bairro,
                cidade         = s.address.cidade,
                estado         = s.address.uf,
                numero         = s.address.numero.takeIf { it.isNotBlank() },
                complemento    = s.address.complemento.takeIf { it.isNotBlank() },
                generosMusicais= s.generosMusicaisSelected.toList(),
                fotoPerfil     = s.profileImageUri?.toString()
            )

            when (val result = registerUserUseCase(user)) {
                is AppResult.Success -> {
                    clearFormUseCase()
                    _event.emit(SignUpEvent.NavigateToLogin)
                }
                is AppResult.Error -> {
                    _event.emit(
                        SignUpEvent.ShowError(result.exception.message ?: "Erro ao cadastrar usuário")
                    )
                }
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}