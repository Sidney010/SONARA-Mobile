package com.example.sonara.core.ui.mask

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*

class CpfVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text

        val digits = text.text.filter { it.isDigit() }.take(11)

        // 🛡️ proteção extra
        if (digits.isEmpty()) {
            return TransformedText(
                AnnotatedString(""),
                OffsetMapping.Identity
            )
        }

        val formatted = buildString {
            digits.forEachIndexed { index, c ->
                append(c)
                when (index) {
                    2, 5 -> append(".")
                    8 -> append("-")
                }
            }
        }

        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset + 1
                    offset <= 8 -> offset + 2
                    offset <= 11 -> offset + 3
                    else -> formatted.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 6 -> offset - 1
                    offset <= 10 -> offset - 2
                    offset <= 14 -> offset - 3
                    else -> digits.length
                }
            }
        }

        return TransformedText(
            AnnotatedString(formatted),
            offsetMapping
        )
    }
}