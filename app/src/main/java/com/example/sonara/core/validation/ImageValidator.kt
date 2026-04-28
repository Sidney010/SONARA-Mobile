package com.example.sonara.core.validation

import android.net.Uri

object ImageValidator : Validator<Uri?> {
    override fun validate(value: Uri?): ValidationResult {
        return when {
            value == null -> ValidationResult.Error("Selecione uma imagem")
            value.toString().isBlank() -> ValidationResult.Error("Imagem inválida")
            else -> ValidationResult.Success
        }
    }
}