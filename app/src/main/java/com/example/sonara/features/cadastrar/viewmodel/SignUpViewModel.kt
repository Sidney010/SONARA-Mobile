package com.example.sonara.features.cadastrar.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sonara.core.validation.CpfValidator
import com.example.sonara.core.validation.EmailValidator
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

        val nomeError = NomeValidator.validate(_uiState.value.nome.value).getErrorOrNull()
        val emailError = EmailValidator.validate(_uiState.value.email.value).getErrorOrNull()
        val cpfError = CpfValidator.validate(uiState.value.cpf.value).getErrorOrNull()
        val passwordError = PasswordValidator.validate(_uiState.value.password.value).getErrorOrNull()
        val userTypeResult = UserTypeValidator.validate(_uiState.value.userType.value)

        val emailAgainError =
            if (_uiState.value.emailAgain.value != _uiState.value.email.value)
                "Emails não conferem"
            else null

        val passwordAgainError =
            if (_uiState.value.passwordAgain.value != _uiState.value.password.value)
                "Senhas não conferem"
            else null

        _uiState.value = _uiState.value.copy(
            nome = _uiState.value.nome.copy(error = nomeError),
            email = _uiState.value.email.copy(error = emailError),
            cpf = _uiState.value.cpf.copy(error = cpfError),
            password = _uiState.value.password.copy(error = passwordError),
            userType = _uiState.value.userType.copy(error = userTypeResult.getErrorOrNull())
        )

        return listOf(
            nomeError,
            emailError,
            cpfError,
            passwordError,
            emailAgainError,
            passwordAgainError,
            userTypeResult.getErrorOrNull()
        ).all { it == null }
    }


}