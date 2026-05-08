package com.example.sonara.core.form

data class SelectionFieldState<T>(
    val value: T,
    val error: String? = null,
    val isTouched: Boolean = false
)