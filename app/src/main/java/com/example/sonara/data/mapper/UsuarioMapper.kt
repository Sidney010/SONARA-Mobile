package com.example.sonara.data.mapper

import com.example.sonara.data.remote.dto.request.CreateUsuarioRequestDto
import com.example.sonara.data.remote.dto.response.LoginResponseDto
import com.example.sonara.data.remote.dto.response.UsuarioResponseDto
import com.example.sonara.domain.model.LoginResult
import com.example.sonara.domain.model.Usuario
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// ── Usuario → Request DTO ─────────────────────────────────────────────────────
// CORREÇÃO: o mapper anterior ignorava a maioria dos campos obrigatórios da API
fun Usuario.toRequestDto() = CreateUsuarioRequestDto(
    nome              = nome,
    email             = email,
    senha             = senha,
    cpf               = cpf.filter { it.isDigit() },             // remove máscara
    data_nasc         = dataNascimento,                          // "yyyy-MM-dd"
    nacionalidade_id  = nacionalidadeId ?: 9,                    // default: Brasileiro
    genero_id         = generoId ?: 1,
    criado            = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
    ultima_atualizacao= LocalDate.now().format(DateTimeFormatter.ISO_DATE),
    telefone          = telefone ?: "",
    tipo_usuario      = tipoUsuario,
    nome_artistico    = nomeArtistico,
    descricao         = descricao,
    cep               = cep ?: "",
    cidade            = cidade ?: "",
    estado            = estado ?: "",
    logradouro        = logradouro ?: "",
    numero            = numero,
    complemento       = complemento,
    bairro            = bairro ?: "",
    longitude         = longitude,
    latitude          = latitude,
    generos_musicais  = generosMusicais,
    foto_perfil       = fotoPerfil
)

// ── Response DTO → Domain ────────────────────────────────────────────────────
// CORREÇÃO: campos corretos do DTO (id_usuario, data_nasc, foto_perfil)
fun UsuarioResponseDto.toDomain() = Usuario(
    id             = id_usuario,       // era 'id' — campo não existe no DTO
    nome           = nome,
    email          = email,
    senha          = "",
    cpf            = cpf,
    dataNascimento = data_nasc ?: "", // era 'dataNascimento' — campo errado
    fotoPerfil     = foto_perfil      // era 'fotoPerfil' — campo errado
)

// ── Login Response → Domain ──────────────────────────────────────────────────
fun LoginResponseDto.toDomain() = LoginResult(
    token   = token,
    usuario = Usuario(
        id             = usuario.id_usuario,
        nome           = usuario.nome,
        email          = usuario.email,
        senha          = "",
        cpf            = usuario.cpf ?: "",
        dataNascimento = usuario.data_nasc ?: "",
        telefone       = usuario.telefone,
        nacionalidadeId= usuario.nacionalidade_id,
        generoId       = usuario.genero_id
    )
)