package com.example.sonara.core.validation

fun ValidationResult.getErrorOrNull(): String? {
    return when (this) {
        is ValidationResult.Success -> null
        is ValidationResult.Error -> this.message
    }
}