package com.example.sonara.core.validation

object CpfValidator : Validator<String> {

    override fun validate(value: String): ValidationResult {

        val cpf = value.filter { it.isDigit() }

        if (cpf.isEmpty()) {
            return ValidationResult.Error("Campo obrigatório")
        }

        if (cpf.length != 11) {
            return ValidationResult.Error("CPF inválido")
        }

        // evita sequências tipo 11111111111
        if (cpf.all { it == cpf.first() }) {
            return ValidationResult.Error("CPF inválido")
        }

        val numbers = cpf.map { it.digitToInt() }

        val firstDigit = calculateDigit(numbers, 9)
        val secondDigit = calculateDigit(numbers, 10)

        if (numbers[9] != firstDigit || numbers[10] != secondDigit) {
            return ValidationResult.Error("CPF inválido")
        }

        return ValidationResult.Success
    }

    private fun calculateDigit(numbers: List<Int>, length: Int): Int {
        val sum = (0 until length).sumOf {
            numbers[it] * ((length + 1) - it)
        }

        val result = (sum * 10) % 11
        return if (result == 10) 0 else result
    }
}