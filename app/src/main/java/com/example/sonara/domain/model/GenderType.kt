package com.example.sonara.domain.model

enum class Gender(val apiId: Int) {
    MASCULINO(1),
    FEMININO(2),
    NAO_BINARIO(3),
    PREFIRO_NAO_INFORMAR(4)
}

fun Gender.toDisplayName(): String {
    return when (this) {
        Gender.MASCULINO -> "Masculino"
        Gender.FEMININO -> "Feminino"
        Gender.NAO_BINARIO -> "Não-binário"
        Gender.PREFIRO_NAO_INFORMAR -> "Prefiro não informar"
    }
}