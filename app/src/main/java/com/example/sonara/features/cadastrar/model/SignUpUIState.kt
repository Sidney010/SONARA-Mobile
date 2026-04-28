package com.example.sonara.features.cadastrar.model

import android.net.Uri
import com.example.sonara.core.form.FieldState
import com.example.sonara.core.form.SelectionFieldState
import com.example.sonara.domain.model.UserType

data class SignUpUIState (
    val nome: FieldState = FieldState(),
    val cpf: FieldState = FieldState(),
    val dataNascimento: FieldState = FieldState(),
    val email: FieldState = FieldState(),
    val emailAgain: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val passwordAgain: FieldState = FieldState(),
    val userType: SelectionFieldState<UserType> = SelectionFieldState(),
    val profileImageUri: Uri? = null,
    val profileImageError: String? = null,
    val isLoading: Boolean = false
)