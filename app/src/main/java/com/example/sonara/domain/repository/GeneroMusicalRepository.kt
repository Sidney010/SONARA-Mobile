package com.example.sonara.domain.repository

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.GeneroMusical

interface GeneroMusicalRepository {
    suspend fun listarGeneroMusical(): AppResult<List<GeneroMusical>>
}