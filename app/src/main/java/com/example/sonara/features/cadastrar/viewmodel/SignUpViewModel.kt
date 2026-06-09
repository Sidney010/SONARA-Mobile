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
import com.example.sonara.domain.model.TipoRedeSocial
import com.example.sonara.domain.model.UserType
import com.example.sonara.domain.model.Usuario
import com.example.sonara.core.validation.UrlValidator
import com.example.sonara.core.validation.ValidationResult
import com.example.sonara.domain.model.RedeSocial
import com.example.sonara.domain.usecase.BuscarEnderecoPorCepUseCase
import com.example.sonara.domain.usecase.ClearFormUseCase
import com.example.sonara.domain.usecase.CreateRedeSocialUseCase
import com.example.sonara.domain.usecase.GetFormUseCase
import com.example.sonara.domain.usecase.ListarGenerosMusicaisUseCase
import com.example.sonara.domain.usecase.ListarNacionalidadesUseCase
import com.example.sonara.domain.usecase.ListarTiposRedesSociaisUseCase
import com.example.sonara.domain.usecase.ProcessImageUseCase
import com.example.sonara.domain.usecase.RegisterUserUseCase
import com.example.sonara.domain.usecase.SaveFormUseCase
import com.example.sonara.features.cadastrar.event.SignUpEvent
import com.example.sonara.features.cadastrar.form.AddressFormManager
import com.example.sonara.features.cadastrar.model.RedeSocialDraft
import com.example.sonara.features.cadastrar.model.SignUpStep
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
    private val listarTiposRedesSociaisUseCase: ListarTiposRedesSociaisUseCase,
    private val createRedeSocialUseCase: CreateRedeSocialUseCase,
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

    private fun List<RedeSocialDraft>.serializeToString(): String =
        filter { it.tipo != null && it.link.isNotBlank() }
            .joinToString("|") { draft ->
                "${draft.tipo!!.id}:${draft.link.trim()}"
            }

    private fun String.deserializeToRedeSocialDrafts(
        tiposDisponiveis: List<TipoRedeSocial>
    ): List<RedeSocialDraft> {
        if (isBlank()) return emptyList()
        return split("|").mapNotNull { entry ->
            val colonIndex = entry.indexOf(':')
            if (colonIndex < 0) return@mapNotNull null
            val tipoId = entry.substring(0, colonIndex).toIntOrNull() ?: return@mapNotNull null
            val link   = entry.substring(colonIndex + 1)
            val tipo   = tiposDisponiveis.find { it.id == tipoId } ?: return@mapNotNull null
            RedeSocialDraft(tipo = tipo, link = link)
        }
    }
    // ── Catálogos ──────────────────────────────────────────────────────

    private fun loadCatalogs() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingCatalogs = true)
            val nacs    = (listarNacionalidadesUseCase()     as? AppResult.Success)?.data ?: emptyList()
            val gens    = (listarGenerosMusicaisUseCase()    as? AppResult.Success)?.data ?: emptyList()
            val tiposRS = (listarTiposRedesSociaisUseCase()  as? AppResult.Success)?.data ?: emptyList()

            _uiState.value = _uiState.value.copy(
                nacionalidades             = nacs,
                generosMusicaisDisponiveis = gens,
                tiposRedesSociais          = tiposRS,
                isLoadingCatalogs          = false
            )

            // Se restoreForm() já rodou mas os tipos ainda não existiam,
            // tenta restaurar as redes sociais agora que tiposRS chegou.
            if (_uiState.value.redesSociais.isEmpty() && tiposRS.isNotEmpty()) {
                getFormUseCase().collect { form ->
                    if (form.redesSociais.isNotBlank()) {
                        val redesRestauradas = form.redesSociais
                            .deserializeToRedeSocialDrafts(tiposRS)
                        if (redesRestauradas.isNotEmpty()) {
                            _uiState.value = _uiState.value.copy(redesSociais = redesRestauradas)
                        }
                    }
                    return@collect   // coleta apenas uma vez
                }
            }
        }
    }
    // ── Restauração do formulário ──────────────────────────────────────

    private fun restoreForm() {
        viewModelScope.launch {
            getFormUseCase().collect { form ->
                if (_uiState.value.nome.value.isBlank() && form.name.isNotBlank()) {

                    // Restaura redes sociais apenas se os catálogos já chegaram
                    val redesRestauradas = if (_uiState.value.tiposRedesSociais.isNotEmpty()) {
                        form.redesSociais.deserializeToRedeSocialDrafts(_uiState.value.tiposRedesSociais)
                    } else {
                        emptyList() // será restaurado pela segunda coleta após loadCatalogs()
                    }

                    _uiState.value = _uiState.value.copy(
                        nome           = FieldState(form.name),
                        email          = FieldState(form.email),
                        cpf            = FieldState(form.cpf),
                        dataNascimento = FieldState(form.dataNasc),
                        telefone       = FieldState(form.telefone),
                        nomeArtistico  = FieldState(form.nomeArtistico),
                        descricao      = FieldState(form.descricao),
                        profileImageUri = form.image?.let { Uri.parse(it) },
                        userType = _uiState.value.userType.copy(
                            value = UserType.entries.find { it.apiValue == form.userType }
                        ),
                        gender = _uiState.value.gender.copy(
                            value = Gender.entries.find { it.apiId.toString() == form.generoId }
                        ),
                        address = _uiState.value.address.copy(
                            cep         = form.cep,
                            rua         = form.rua,
                            bairro      = form.bairro,
                            cidade      = form.cidade,
                            uf          = form.uf,
                            numero      = form.numero,
                            complemento = form.complemento
                        ),
                        redesSociais = redesRestauradas   // <-- novo campo restaurado
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
        scheduleSaveForm()
    }

    /** Armazena apenas dígitos (máx 11); a máscara é aplicada pelo VisualTransformation na UI. */
    fun onTelefoneChange(v: String) {
        val digits = v.filter { it.isDigit() }.take(11)
        _uiState.value = _uiState.value.copy(
            telefone = _uiState.value.telefone.copy(value = digits)
        )
        scheduleSaveForm()
    }

    fun onEmailChange(v: String) {
        _uiState.value = _uiState.value.copy(
            email = _uiState.value.email.copy(
                value = v,
                error = EmailValidator.validate(v).getErrorOrNull()
            )
        )
        scheduleSaveForm()
    }

    fun onEmailAgainChange(v: String) {
        val error = if (v != _uiState.value.email.value) "Emails não coincidem" else null
        _uiState.value = _uiState.value.copy(
            emailAgain = _uiState.value.emailAgain.copy(value = v, error = error)
        )
        scheduleSaveForm()
    }

    fun onPasswordChange(v: String) {
        _uiState.value = _uiState.value.copy(
            password = _uiState.value.password.copy(
                value = v,
                error = PasswordValidator.validate(v).getErrorOrNull()
            )
        )
        scheduleSaveForm()
    }

    fun onPasswordAgainChange(v: String) {
        val error = if (v != _uiState.value.password.value) "Senhas não coincidem" else null
        _uiState.value = _uiState.value.copy(
            passwordAgain = _uiState.value.passwordAgain.copy(value = v, error = error)
        )
        scheduleSaveForm()
    }

    fun onUserTypeChange(type: UserType) {
        _uiState.value = _uiState.value.copy(
            userType = _uiState.value.userType.copy(value = type, error = null)
        )
        scheduleSaveForm()
    }

    fun onGenderChange(gender: Gender) {
        _uiState.value = _uiState.value.copy(
            gender = _uiState.value.gender.copy(value = gender, error = null)
        )
        scheduleSaveForm()
    }

    fun onNacionalidadeChange(nac: Nacionalidade) {
        _uiState.value = _uiState.value.copy(
            nacionalidade = _uiState.value.nacionalidade.copy(value = nac)
        )
        scheduleSaveForm()
    }

    fun onGeneroMusicalToggle(id: Int) {
        val current = _uiState.value.generosMusicaisSelected
        _uiState.value = _uiState.value.copy(
            generosMusicaisSelected = if (id in current) current - id else current + id,
            generosMusicaisError    = null
        )
        scheduleSaveForm()
    }

    fun onNomeArtisticoChange(v: String) {
        _uiState.value = _uiState.value.copy(
            nomeArtistico = _uiState.value.nomeArtistico.copy(value = v)
        )
        scheduleSaveForm()
    }

    fun onDescricaoChange(v: String) {
        _uiState.value = _uiState.value.copy(
            descricao = _uiState.value.descricao.copy(value = v)
        )
        scheduleSaveForm()
    }

    fun onAddRedeSocial() {
        val current = _uiState.value.redesSociais
        _uiState.value = _uiState.value.copy(
            redesSociais = current + RedeSocialDraft()
        )
        scheduleSaveForm()
    }

    fun onRemoveRedeSocial(index: Int) {
        val current = _uiState.value.redesSociais.toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            _uiState.value = _uiState.value.copy(redesSociais = current)
            scheduleSaveForm()
        }
    }

    fun onRedeSocialLinkChange(index: Int, link: String) {
        val current = _uiState.value.redesSociais.toMutableList()
        if (index in current.indices) {
            current[index] = current[index].copy(link = link)
            _uiState.value = _uiState.value.copy(redesSociais = current)
            scheduleSaveForm()
        }
    }

    fun onRedeSocialTipoChange(index: Int, tipo: TipoRedeSocial) {
        val current = _uiState.value.redesSociais.toMutableList()
        if (index in current.indices) {
            current[index] = current[index].copy(tipo = tipo)
            _uiState.value = _uiState.value.copy(redesSociais = current)
            scheduleSaveForm()
        }
    }

    // ── Endereço ────────────────────────────────────────────────────────

    fun onCepChange(v: String) {
        val newAddr = addressManager.updateCep(_uiState.value.address, v)
        _uiState.value = _uiState.value.copy(address = newAddr)
        scheduleSaveForm()
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
        scheduleSaveForm()
    }

    fun onBairroChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = addressManager.updateBairro(_uiState.value.address, v)
        )
        scheduleSaveForm()
    }

    fun onCidadeChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = addressManager.updateCidade(_uiState.value.address, v)
        )
        scheduleSaveForm()
    }

    fun onUfChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = addressManager.updateUf(_uiState.value.address, v)
        )
        scheduleSaveForm()
    }

    fun onNumeroChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address.copy(numero = v)
        )
        scheduleSaveForm()
    }

    fun onComplementoChange(v: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address.copy(complemento = v)
        )
        scheduleSaveForm()
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
                is AppResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        profileImageUri   = r.data,
                        profileImageError = null,
                        isImageLoading    = false
                    )
                    scheduleSaveForm()
                }
                is AppResult.Error   -> _uiState.value = _uiState.value.copy(
                    profileImageError = "Erro ao processar imagem",
                    isImageLoading    = false
                )
            }
        }
    }

    // ── Navegação entre etapas ──────────────────────────────────────────

    fun nextStep() {
        val state = _uiState.value
        when (state.currentStep) {
            SignUpStep.PERSONAL_DATA -> {
                if (validatePersonalData()) {
                    _uiState.value = state.copy(currentStep = SignUpStep.PROFILE_DATA)
                }
            }
            SignUpStep.PROFILE_DATA -> {
                if (validateProfileData()) {
                    _uiState.value = state.copy(currentStep = SignUpStep.ADDRESS)
                }
            }
            SignUpStep.ADDRESS -> {
                onRegisterClick()
            }
        }
    }

    fun previousStep() {
        val state = _uiState.value
        val prevStep = when (state.currentStep) {
            SignUpStep.PERSONAL_DATA -> SignUpStep.PERSONAL_DATA
            SignUpStep.PROFILE_DATA -> SignUpStep.PERSONAL_DATA
            SignUpStep.ADDRESS -> SignUpStep.PROFILE_DATA
        }
        _uiState.value = state.copy(currentStep = prevStep)
    }

    private fun validatePersonalData(): Boolean {
        val state = _uiState.value
        val nomeError = NomeValidator.validate(state.nome.value).getErrorOrNull()
        val cpfError = CpfValidator.validate(state.cpf.value).getErrorOrNull()
        val userTypeError = UserTypeValidator.validate(state.userType.value).getErrorOrNull()
        val genderError = GenderValidator.validate(state.gender.value).getErrorOrNull()

        _uiState.value = state.copy(
            nome = state.nome.copy(error = nomeError),
            cpf = state.cpf.copy(error = cpfError),
            userType = state.userType.copy(error = userTypeError),
            gender = state.gender.copy(error = genderError)
        )

        return listOf(nomeError, cpfError, userTypeError, genderError).all { it == null }
    }

    private fun validateProfileData(): Boolean {
        val state = _uiState.value
        val isArtista = state.userType.value == UserType.ARTISTA

        val emailError = EmailValidator.validate(state.email.value).getErrorOrNull()
        val emailAgainError = if (state.emailAgain.value != state.email.value) "Emails não coincidem" else null
        val passwordError = PasswordValidator.validate(state.password.value).getErrorOrNull()
        val passwordAgainError = if (state.passwordAgain.value != state.password.value) "Senhas não coincidem" else null
        val generosMusicaisError = if (isArtista && state.generosMusicaisSelected.isEmpty())
            "Selecione pelo menos um gênero musical" else null

        _uiState.value = state.copy(
            email = state.email.copy(error = emailError),
            emailAgain = state.emailAgain.copy(error = emailAgainError),
            password = state.password.copy(error = passwordError),
            passwordAgain = state.passwordAgain.copy(error = passwordAgainError),
            generosMusicaisError = generosMusicaisError
        )

        return listOf(emailError, emailAgainError, passwordError, passwordAgainError, generosMusicaisError).all { it == null }
    }

    private fun validateAddress(): Boolean {
        val state = _uiState.value
        val cepError = CepValidator.validate(state.address.cep)
        val ruaError = NomeValidator.validate(state.address.rua).getErrorOrNull()
        val bairroError = NomeValidator.validate(state.address.bairro).getErrorOrNull()
        val cidadeError = NomeValidator.validate(state.address.cidade).getErrorOrNull()
        val ufError = if (state.address.uf.isBlank()) "Campo obrigatório" else null

        _uiState.value = state.copy(
            address = state.address.copy(
                cepError = cepError,
                ruaError = ruaError,
                bairroError = bairroError,
                cidadeError = cidadeError,
                ufError = ufError
            )
        )

        return listOf(cepError, ruaError, bairroError, cidadeError, ufError).all { it == null }
    }

    // ── Cadastro ────────────────────────────────────────────────────────

    fun onRegisterClick() {
        if (!validateAddress()) return

        val state     = _uiState.value
        val isArtista = state.userType.value == UserType.ARTISTA

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
                    val userId = result.data.id ?: 0

                    // Criação de múltiplas redes sociais
                    state.redesSociais.forEach { draft ->
                        if (draft.link.isNotBlank() && draft.tipo != null && UrlValidator.validate(draft.link) is ValidationResult.Success) {
                            createRedeSocialUseCase(
                                RedeSocial(
                                    link = draft.link.trim(),
                                    tipoId = draft.tipo.id,
                                    usuarioId = userId
                                )
                            )
                        }
                    }

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
                    name            = s.nome.value,
                    email           = s.email.value,
                    cpf             = s.cpf.value,
                    password        = s.password.value,
                    image           = s.profileImageUri?.toString(),
                    dataNasc        = s.dataNascimento.value,
                    telefone        = s.telefone.value,
                    userType        = s.userType.value?.apiValue ?: "",
                    nomeArtistico   = s.nomeArtistico.value,
                    descricao       = s.descricao.value,
                    nacionalidadeId = s.nacionalidade.value?.id?.toString() ?: "",
                    generoId        = s.gender.value?.apiId?.toString() ?: "",
                    generosMusicais = s.generosMusicaisSelected.joinToString(","),
                    cep             = s.address.cep,
                    rua             = s.address.rua,
                    bairro          = s.address.bairro,
                    cidade          = s.address.cidade,
                    uf              = s.address.uf,
                    numero          = s.address.numero,
                    complemento     = s.address.complemento,
                    redesSociais    = s.redesSociais.serializeToString()   // <-- novo campo salvo
                )
            )
        }
    }
}