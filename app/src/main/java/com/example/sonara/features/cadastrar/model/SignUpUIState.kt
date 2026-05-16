package com.example.sonara.features.cadastrar.model

import android.net.Uri
import com.example.sonara.core.form.FieldState
import com.example.sonara.core.form.SelectionFieldState
import com.example.sonara.domain.model.Gender
import com.example.sonara.domain.model.GeneroMusical
import com.example.sonara.domain.model.Nacionalidade
import com.example.sonara.domain.model.UserType

data class SignUpUIState(
    // ── Dados pessoais ────────────────────────────────────────────────────────
    val nome: FieldState           = FieldState(),
    val cpf: FieldState            = FieldState(),
    val dataNascimento: FieldState = FieldState(),   // "yyyy-MM-dd"
    val telefone: FieldState       = FieldState(),
    val email: FieldState          = FieldState(),
    val emailAgain: FieldState     = FieldState(),
    val password: FieldState       = FieldState(),
    val passwordAgain: FieldState  = FieldState(),

    // ── Tipo de usuário
    val userType: SelectionFieldState<UserType?> = SelectionFieldState(value = null),

    // ── Gênero da pessoa
    val gender: SelectionFieldState<Gender?> = SelectionFieldState(value = null),

    // ── Nacionalidade (carregada da API)
    val nacionalidade: SelectionFieldState<Nacionalidade?> = SelectionFieldState(value = null),
    val nacionalidades: List<Nacionalidade> = emptyList(),

    // ── Gêneros musicais (multi-select, carregados da API) ────────────────────
    val generosMusicaisSelected: Set<Int> = emptySet(),   // IDs selecionados
    val generosMusicaisError: String?     = null,
    val generosMusicaisDisponiveis: List<GeneroMusical> = emptyList(),

    // Dados artísticos (opcionais conforme tipo)
    val nomeArtistico: FieldState = FieldState(),
    val descricao: FieldState     = FieldState(),

    // Foto de perfil
    val profileImageUri: Uri?     = null,
    val profileImageError: String? = null,
    val isImageLoading: Boolean   = false,

    // Endereço
    val address: AddressUiState   = AddressUiState(),

    // Controle de UI
    val isLoading: Boolean        = false,
    val isLoadingCatalogs: Boolean = false
)