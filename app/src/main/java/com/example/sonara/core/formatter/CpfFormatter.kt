package com.example.sonara.core.formatter

object CpfFormatter {

    fun format(value: String): String {
        val digits = value.filter { it.isDigit() }.take(11)

        return buildString {
            digits.forEachIndexed { index, c ->
                append(c)
                when (index) {
                    2, 5 -> append(".")
                    8 -> append("-")
                }
            }
        }
    }
}