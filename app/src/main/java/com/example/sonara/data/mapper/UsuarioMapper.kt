package com.example.sonara.data.mapper

import com.example.sonara.data.remote.dto.request.CreateUsuarioRequestDto
import com.example.sonara.data.remote.dto.response.UsuarioResponseDto
import com.example.sonara.domain.model.Usuario

fun Usuario.toRequestDto() =
    CreateUsuarioRequestDto(
        nome = nome,
        email = email,
        senha = senha,
        cpf = cpf,
        data_nasc = dataNascimento,
        foto_perfil = fotoPerfil
    )

fun UsuarioResponseDto.toDomain() =
    Usuario(
        id = id,
        nome = nome,
        email = email,
        senha = "",
        cpf = cpf,
        dataNascimento = dataNascimento,
        fotoPerfil = fotoPerfil
    )