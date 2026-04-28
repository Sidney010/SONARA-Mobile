package com.example.sonara.features.cadastrar.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sonara.core.validation.CpfValidator
import com.example.sonara.core.validation.EmailValidator
import com.example.sonara.core.validation.ImageValidator
import com.example.sonara.core.validation.NomeValidator
import com.example.sonara.core.validation.PasswordValidator
import com.example.sonara.core.validation.getErrorOrNull
import com.example.sonara.domain.model.UserType
import com.example.sonara.features.cadastrar.model.SignUpUIState
import com.example.sonara.features.cadastrar.validation.UserTypeValidator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SignUpViewModel: ViewModel() {
    //Colocar issso na hora de enviar para o backend
    // val formattedCpf = CpfFormatter.format(uiState.cpf.value)
    private val _uiState = mutableStateOf(SignUpUIState())

    val uiState: State<SignUpUIState> get() = _uiState

    private val _event = MutableSharedFlow<SignUpUIState>()
    val event = _event.asSharedFlow()

    fun onNomeChange(newNome: String) {
        val current = _uiState.value.nome
        val error = NomeValidator.validate(newNome).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            nome = current.copy(
                value = newNome,
                error = error
            )
        )
    }

    fun onEmailChange(newEmail: String) {
        val current = _uiState.value.email
        val error = EmailValidator.validate(newEmail).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            email = current.copy(
                value = newEmail,
                error = error
            )
        )
    }

    fun onEmailAgainChange(newEmailAgain: String) {
        val current = _uiState.value.emailAgain
        val error = EmailValidator.validate(newEmailAgain).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            emailAgain = current.copy(
                value = newEmailAgain,
                error = error
            )
        )
    }

    fun onProfileImageSelected(uri: Uri?) {
        val error = ImageValidator.validate(uri).getErrorOrNull()
        _uiState.value = _uiState.value.copy(
            profileImageUri = uri,
            profileImageError = error
        )
    }

    fun onPasswordChange(newPassword: String) {
        val error = PasswordValidator.validate(newPassword).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            password = _uiState.value.password.copy(
                value = newPassword,
                error = error,
                isTouched = true
            )
        )
    }

    fun onCpfChange(newCpf: String) {
        val digitsOnly = newCpf.filter { it.isDigit() }.take(11)
        val current = _uiState.value.cpf
        val error = CpfValidator.validate(digitsOnly).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            cpf = current.copy(
                value = newCpf,
                error = error
            )
        )
    }
    fun onPasswordAgainChange(newPasswordAgain: String) {
        val error = PasswordValidator.validate(newPasswordAgain).getErrorOrNull()

        _uiState.value = _uiState.value.copy(
            passwordAgain = _uiState.value.passwordAgain.copy(
                value = newPasswordAgain,
                error = error,
                isTouched = true
            )
        )
    }
    fun onUserTypeChange(type: UserType) {
        val current = _uiState.value.userType

        _uiState.value = _uiState.value.copy(
            userType = current.copy(
                value = type,
                error = null,
                isTouched = true
            )
        )
    }

    fun validateAll(): Boolean {
        val current = _uiState.value

        val nomeError = NomeValidator.validate(current.nome.value).getErrorOrNull()
        val emailError = EmailValidator.validate(current.email.value).getErrorOrNull()
        val cpfError = CpfValidator.validate(current.cpf.value).getErrorOrNull()
        val passwordError = PasswordValidator.validate(current.password.value).getErrorOrNull()
        val imageError = ImageValidator.validate(current.profileImageUri).getErrorOrNull()

        val emailAgainError =
            if (current.emailAgain.value != current.email.value)
                "Emails não conferem" else null

        val passwordAgainError =
            if (current.passwordAgain.value != current.password.value)
                "Senhas não conferem" else null

        val userTypeError =
            UserTypeValidator.validate(current.userType.value).getErrorOrNull()

        _uiState.value = current.copy(
            nome = current.nome.copy(error = nomeError),
            email = current.email.copy(error = emailError),
            cpf = current.cpf.copy(error = cpfError),
            password = current.password.copy(error = passwordError),
            profileImageError = imageError,
            userType = current.userType.copy(error = userTypeError)
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

        // Aqui vai:
        // - chamada de UseCase (futuro)
        // - ou emitir evento

        // Exemplo simples:
        // _event.emit(NavigateNext)
    }


}