package com.example.sonara.features.cadastrar.validation

import com.example.sonara.core.validation.*
import com.example.sonara.features.cadastrar.model.SignUpUIState

object SignUpValidator {

    fun validate(
        state: SignUpUIState
    ): Boolean {

        return listOf(

            NomeValidator
                .validate(state.nome.value)
                .getErrorOrNull(),

            EmailValidator
                .validate(state.email.value)
                .getErrorOrNull(),

            PasswordValidator
                .validate(state.password.value)
                .getErrorOrNull(),

            CpfValidator
                .validate(state.cpf.value)
                .getErrorOrNull(),

            CepValidator
                .validate(state.address.cep)

        ).all { it == null }
    }
}