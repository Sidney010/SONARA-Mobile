package com.example.sonara.features.cadastrar.form

import com.example.sonara.features.cadastrar.model.AddressUiState

class AddressFormManager {

    fun updateCep(
        state: AddressUiState,
        value: String
    ): AddressUiState {

        return state.copy(
            cep = value
                .filter { it.isDigit() }
                .take(8)
        )
    }

    fun updateRua(
        state: AddressUiState,
        value: String
    ): AddressUiState {

        return state.copy(
            rua = value
        )
    }

    fun updateBairro(
        state: AddressUiState,
        value: String
    ): AddressUiState {

        return state.copy(
            bairro = value
        )
    }

    fun updateCidade(
        state: AddressUiState,
        value: String
    ): AddressUiState {

        return state.copy(
            cidade = value
        )
    }

    fun updateUf(
        state: AddressUiState,
        value: String
    ): AddressUiState {

        return state.copy(
            uf = value.take(2)
        )
    }
}