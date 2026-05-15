package com.example.sonara.data.mapper

import com.example.sonara.data.remote.dto.response.NacionalidadeDto
import com.example.sonara.domain.model.Nacionalidade

fun NacionalidadeDto.toDomain() = Nacionalidade(
    id = id_nacionalidade,
    nome = nome
)
