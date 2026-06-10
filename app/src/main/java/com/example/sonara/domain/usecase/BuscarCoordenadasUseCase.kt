package com.example.sonara.domain.usecase

import com.example.sonara.core.common.AppResult
import com.example.sonara.domain.repository.EnderecoRepository
import javax.inject.Inject

class BuscarCoordenadasUseCase @Inject constructor(
    private val repository: EnderecoRepository
) {
    suspend operator fun invoke(endereco: String): AppResult<Pair<Double, Double>> {
        return repository.buscarCoordenadas(endereco)
    }
}