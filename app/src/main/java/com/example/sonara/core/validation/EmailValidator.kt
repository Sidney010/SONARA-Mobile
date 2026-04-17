package com.example.sonara.core.validation

import android.util.Patterns

object EmailValidator : Validator<String> {

    override fun validate(value: String): ValidationResult {
        if (value.isBlank()) return ValidationResult.Error("Campo obrigatório")
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            return ValidationResult.Error("Email inválido")
        }
        return ValidationResult.Success
    }
}