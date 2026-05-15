package com.example.sonara.data.mapper

import com.example.sonara.data.remote.dto.response.GeneroMusicalDto
import com.example.sonara.domain.model.GeneroMusical

fun GeneroMusicalDto.toDomain() = GeneroMusical(
    id = id_genero_musical,
    nome = nome
)