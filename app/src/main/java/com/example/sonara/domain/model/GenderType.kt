package com.example.sonara.domain.model

enum class Gender {
    MASCULINO,
    FEMININO,
    NAO_BINARIO,
    PREFIRO_NAO_INFORMAR
}
fun Gender.toDisplayName(): String {
    return when (this) {
        Gender.MASCULINO -> "Masculino"
        Gender.FEMININO -> "Feminino"
        Gender.NAO_BINARIO -> "Não-binário"
        Gender.PREFIRO_NAO_INFORMAR -> "Prefiro não informar"
    }
}