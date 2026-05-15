package com.example.sonara.domain.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Nacionalidade

interface NacionalidadeRepository {
    suspend fun listarNacionalidades(): AppResult<List<Nacionalidade>>
}

