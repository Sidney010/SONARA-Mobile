package com.example.sonara.core.validation

import android.net.Uri

object ImageValidator {
    fun validate(value: Uri?): ValidationResult {
        return if (value == null)
            ValidationResult.Error("Selecione uma imagem")
        else ValidationResult.Success
    }
}