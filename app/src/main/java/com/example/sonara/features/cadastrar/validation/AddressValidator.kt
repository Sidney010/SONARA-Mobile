package com.example.sonara.features.cadastrar.validation

import com.example.sonara.features.cadastrar.model.AddressUiState

object AddressValidator {

    fun validate(
        address: AddressUiState
    ): Map<String, String?> {

        return mapOf(

            "cep" to CepValidator.validate(address.cep),

            "rua" to if (address.rua.isBlank())
                "Rua obrigatória"
            else null,

            "bairro" to if (address.bairro.isBlank())
                "Bairro obrigatório"
            else null,

            "cidade" to if (address.cidade.isBlank())
                "Cidade obrigatória"
            else null,

            "uf" to if (address.uf.length != 2)
                "UF inválida"
            else null
        )
    }
}