package com.example.sonara.features.cadastrar.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.common.AppResult
import com.example.sonara.core.storage.FormData
import com.example.sonara.core.validation.*
import com.example.sonara.data.remote.dto.UsuarioRequest
import com.example.sonara.domain.model.Gender
import com.example.sonara.domain.model.UserType
import com.example.sonara.domain.usecase.ClearFormUseCase
import com.example.sonara.domain.usecase.GetFormUseCase
import com.example.sonara.domain.usecase.ProcessImageUseCase
import com.example.sonara.domain.usecase.RegisterUserUseCase
import com.example.sonara.domain.usecase.SaveFormUseCase
import com.example.sonara.features.cadastrar.event.SignUpEvent
import com.example.sonara.features.cadastrar.model.SignUpUIState
import com.example.sonara.features.cadastrar.validation.GenderValidator
import com.example.sonara.features.cadastrar.validation.UserTypeValidator
import com.example.sonara.features.trocarrsenha.event.RedefinedPasswordEvent
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
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(SignUpUIState())
    val uiState: State<SignUpUIState> = _uiState

    private val _event = MutableSharedFlow<SignUpEvent>()
    val event = _event.asSharedFlow()

    private var saveJob: Job? = null

    init {
        restoreForm()
    }

    private fun restoreForm() {
        viewModelScope.launch {
            getFormUseCase().collect { data ->

                _uiState.value = _uiState.value.copy(
                    nome = _uiState.value.nome.copy(value = data.name),
                    email = _uiState.value.email.copy(value = data.email),
                    cpf = _uiState.value.cpf.copy(value = data.cpf),
                    password = _uiState.value.password.copy(value = data.password),
                    profileImageUri = data.image?.let { Uri.parse(it) },
                    userType = _uiState.value.userType.copy(
                        value = data.userType
                            .split(",")
                            .mapNotNull {
                                runCatching {
                                    UserType.valueOf(it)
                                }.getOrNull()
                            }
                            .toSet()
                    )
                )
            }
        }
    }

    // debounce profissional
    private fun saveWithDelay() {
        saveJob?.cancel()

        saveJob = viewModelScope.launch {
            delay(500)

            val state = _uiState.value

            saveFormUseCase(
                FormData(
                    name = state.nome.value,
                    email = state.email.value,
                    cpf = state.cpf.value,
                    password = state.password.value,
                    image = state.profileImageUri?.toString(),

                    userType = state.userType.value
                        ?.joinToString(",") { it.name }
                        ?: ""
                )
            )
        }
    }


    fun onImagePicked(context: Context, uri: Uri?) {
        if (uri == null) return

        viewModelScope.launch {
            delay(200)
            _uiState.value = _uiState.value.copy(isImageLoading = true)

            delay(300)
            val result = processImageUseCase(context, uri)

            val finalUri = when (result) {
                is AppResult.Success -> result.data
                is AppResult.Error -> uri
            }

            _uiState.value = _uiState.value.copy(
                profileImageUri = finalUri,
                profileImageError = null,
                isImageLoading = false
            )
            saveWithDelay()
        }
    }

    fun onNomeChange(newNome: String) {
        val error = NomeValidator.validate(newNome).getErrorOrNull()
        _uiState.value = _uiState.value.copy(
            nome = _uiState.value.nome.copy(value = newNome, error = error)
        )
        saveWithDelay()
    }

    fun onEmailChange(newEmail: String) {
        val error = EmailValidator.validate(newEmail).getErrorOrNull()
        _uiState.value = _uiState.value.copy(
            email = _uiState.value.email.copy(value = newEmail, error = error)
        )
        saveWithDelay()
    }

    fun onEmailAgainChange(newEmailAgain: String) {
        val error = EmailValidator.validate(newEmailAgain).getErrorOrNull()
        _uiState.value = _uiState.value.copy(
            emailAgain = _uiState.value.emailAgain.copy(value = newEmailAgain, error = error)
        )
    }

    fun onPasswordChange(newPassword: String) {
        val error = PasswordValidator.validate(newPassword).getErrorOrNull()
        _uiState.value = _uiState.value.copy(
            password = _uiState.value.password.copy(value = newPassword, error = error, isTouched = true)
        )
        saveWithDelay()
    }

    fun onPasswordAgainChange(newPasswordAgain: String) {
        val error = PasswordValidator.validate(newPasswordAgain).getErrorOrNull()
        _uiState.value = _uiState.value.copy(
            passwordAgain = _uiState.value.passwordAgain.copy(value = newPasswordAgain, error = error, isTouched = true)
        )
    }

    fun onCpfChange(newCpf: String) {
        val digitsOnly = newCpf.filter { it.isDigit() }.take(11)
        val error = CpfValidator.validate(digitsOnly).getErrorOrNull()
        _uiState.value = _uiState.value.copy(
            cpf = _uiState.value.cpf.copy(value = newCpf, error = error)
        )
        saveWithDelay()
    }

    fun onUserTypeChange(types: Set<UserType>) {

        _uiState.value = _uiState.value.copy(
            userType = _uiState.value.userType.copy(
                value = types,
                error = null,
                isTouched = true
            )
        )

        saveWithDelay()
    }
    fun onGenderChange(gender: Gender) {

        _uiState.value = _uiState.value.copy(
            gender = _uiState.value.gender.copy(
                value = gender,
                error = null,
                isTouched = true
            )
        )

        saveWithDelay()
    }
    fun validateAll(): Boolean {
        val current = _uiState.value

        val nomeError = NomeValidator.validate(current.nome.value).getErrorOrNull()
        val emailError = EmailValidator.validate(current.email.value).getErrorOrNull()
        val cpfError = CpfValidator.validate(current.cpf.value).getErrorOrNull()
        val passwordError = PasswordValidator.validate(current.password.value).getErrorOrNull()
        val imageError = ImageValidator.validate(current.profileImageUri).getErrorOrNull()

        val emailAgainError =
            if (current.emailAgain.value != current.email.value) "Emails não conferem" else null

        val passwordAgainError =
            if (current.passwordAgain.value != current.password.value) "Senhas não conferem" else null

        val userTypeError =
            UserTypeValidator
                .validate(current.userType.value)
                .getErrorOrNull()

        val genderTypeError =
            GenderValidator
                .validate(current.gender.value)
                .getErrorOrNull()

        _uiState.value = current.copy(
            nome = current.nome.copy(error = nomeError),
            email = current.email.copy(error = emailError),
            cpf = current.cpf.copy(error = cpfError),
            password = current.password.copy(error = passwordError),
            profileImageError = imageError,
            userType = current.userType.copy(error = userTypeError),
            gender = current.gender.copy(error = genderTypeError)
        )

        return listOf(
            nomeError,
            emailError,
            cpfError,
            passwordError,
            emailAgainError,
            passwordAgainError,
            userTypeError,
            imageError
        ).all { it == null }
    }

    fun onRegisterClick() {
        if (!validateAll()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val request = UsuarioRequest(
                nome = uiState.value.nome.value,
                email = uiState.value.email.value,
                senha = uiState.value.password.value,
                cpf = uiState.value.cpf.value,
                data_nasc = "2000-01-01", // Implementar DatePicker futuramente
                foto_perfil = uiState.value.profileImageUri?.toString()
            )

            when (val result = registerUserUseCase(request)) {
                is AppResult.Success -> {
                    clearFormUseCase()
                    _event.emit(SignUpEvent.NavigateToLogin)
                }
                is AppResult.Error -> {
                    _event.emit(SignUpEvent.ShowError("Não foi possível efetuar o cadastro. Verifique sua conexão ou tente novamente."))
                }
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }


}
