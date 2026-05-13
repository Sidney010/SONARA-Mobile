package com.example.sonara.features.cadastrar.form

import com.example.sonara.features.cadastrar.model.SignUpUIState

class SignUpFormManager {

    fun updateNome(
        state: SignUpUIState,
        value: String
    ): SignUpUIState {

        return state.copy(
            nome = state.nome.copy(
                value = value
            )
        )
    }

    fun updateEmail(
        state: SignUpUIState,
        value: String
    ): SignUpUIState {

        return state.copy(
            email = state.email.copy(
                value = value
            )
        )
    }

    fun updateCpf(
        state: SignUpUIState,
        value: String
    ): SignUpUIState {

        return state.copy(
            cpf = state.cpf.copy(
                value = value
            )
        )
    }
}