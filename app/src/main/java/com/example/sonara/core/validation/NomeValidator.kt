package com.example.sonara.core.validation

object NomeValidator: Validator<String>  {

    override fun validate(value: String): ValidationResult {
        if (value.isBlank()) return ValidationResult.Error("Campo obrigatório")
        return ValidationResult.Success
    }
}