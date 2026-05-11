package com.example.sonara.core.error

object ApiErrorHandler {

    fun parse(code: Int): String {

        return when(code) {

            401 -> "Não autorizado"

            404 -> "Não encontrado"

            500 -> "Erro interno"

            else -> "Erro desconhecido"
        }
    }
}