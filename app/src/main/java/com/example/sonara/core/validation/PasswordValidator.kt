package com.example.sonara.core.validation

object PasswordValidator : Validator<String> {

    override fun validate(value: String): ValidationResult {
        if (value.length < 8) return ValidationResult.Error("Mínimo 8 caracteres")
        if (!value.any { it.isUpperCase() }) return ValidationResult.Error("Precisa de letra maiúscula")
        if (!value.any { it.isLowerCase() }) return ValidationResult.Error("Precisa de letra minúscula")
        if (!value.any { it.isDigit() }) return ValidationResult.Error("Precisa de número")
        if (!value.any { !it.isLetterOrDigit() }) return ValidationResult.Error("Precisa de caractere especial")

        return ValidationResult.Success
    }
}