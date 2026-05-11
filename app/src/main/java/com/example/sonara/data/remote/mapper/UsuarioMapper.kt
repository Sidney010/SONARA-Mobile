package com.example.sonara.data.remote.mapper

import com.example.sonara.data.remote.dto.request.CreateUsuarioRequestDto
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