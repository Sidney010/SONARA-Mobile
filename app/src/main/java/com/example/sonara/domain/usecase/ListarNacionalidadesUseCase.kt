package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.model.Nacionalidade
import com.example.sonara.domain.repository.NacionalidadeRepository
import javax.inject.Inject

class ListarNacionalidadesUseCase @Inject constructor(
    private val repository: NacionalidadeRepository
) {
    suspend operator fun invoke(): AppResult<List<Nacionalidade>> {
        return repository.listarNacionalidades()
    }
}