package com.example.sonara.data.mapper

import com.example.sonara.data.remote.dto.response.ViaCepResponseDto
import com.example.sonara.domain.model.Endereco

fun ViaCepResponseDto.toDomain() = Endereco(

    cep = cep,

    rua = logradouro,

    bairro = bairro,

    cidade = localidade,

    uf = uf
)