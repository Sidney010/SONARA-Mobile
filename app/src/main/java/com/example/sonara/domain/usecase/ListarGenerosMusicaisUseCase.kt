package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.GeneroMusical
import com.example.sonara.domain.repository.GeneroMusicalRepository
import javax.inject.Inject

class ListarGenerosMusicaisUseCase @Inject constructor(
    private val repository: GeneroMusicalRepository
) {
    suspend operator fun invoke(): AppResult<List<GeneroMusical>> {
        return repository.listar()
    }
}
