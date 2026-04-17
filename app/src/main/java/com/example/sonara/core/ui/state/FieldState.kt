package com.example.sonara.core.ui.state

data class FieldState(
    val value: String = "",
    val error: String? = null,
    val isTouched: Boolean = false
)