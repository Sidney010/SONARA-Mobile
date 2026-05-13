package com.example.sonara.domain.usecase

import com.example.sonara.domain.repository.EnderecoRepository
import javax.inject.Inject

class BuscarEnderecoPorCepUseCase @Inject constructor(
    private val repository: EnderecoRepository
) {

    suspend operator fun invoke(
        cep: String
    ) = repository.buscarCep(cep)
}