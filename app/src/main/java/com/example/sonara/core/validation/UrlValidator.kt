package com.example.sonara.core.validation

import android.util.Patterns

object UrlValidator : Validator<String> {
    override fun validate(value: String): ValidationResult {
        if (value.isBlank()) {
            return ValidationResult.Error("O link não pode estar vazio")
        }
        return if (Patterns.WEB_URL.matcher(value).matches()) {
            ValidationResult.Success
        } else {
            ValidationResult.Error("URL inválida")
        }
    }
}
