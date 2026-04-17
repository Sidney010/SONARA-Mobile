package com.example.sonara.core.validation

interface Validator<T> {
    fun validate(value: T): ValidationResult
}