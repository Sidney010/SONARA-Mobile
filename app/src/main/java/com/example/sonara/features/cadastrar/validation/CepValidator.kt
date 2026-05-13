package com.example.sonara.features.cadastrar.validation

object CepValidator {

    fun validate(cep: String): String? {

        val digits = cep.filter { it.isDigit() }

        return when {

            digits.isBlank() ->
                "CEP obrigatório"

            digits.length != 8 ->
                "CEP inválido"

            else -> null
        }
    }
}