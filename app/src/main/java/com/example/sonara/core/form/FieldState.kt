package com.example.sonara.core.form

data class FieldState(
    val value: String = "",
    val error: String? = null,
    val isTouched: Boolean = false
)