package com.example.sonara.core.ui.mask

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Máscara de telefone celular: (XX) XXXXX-XXXX
 * O campo deve armazenar apenas dígitos (máx 11).
 */
class TelefoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(11)
        val formatted = buildString {
            digits.forEachIndexed { i, c ->
                when (i) {
                    0    -> append("(")
                    2    -> append(") ")
                    7    -> append("-")
                }
                append(c)
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset == 0) return 0
                var t = offset
                if (offset >= 1) t++          // (
                if (offset >= 2) t += 2        // ) espaço
                if (offset >= 7) t++           // -
                return t.coerceAtMost(formatted.length)
            }
            override fun transformedToOriginal(offset: Int): Int =
                offset.coerceAtMost(digits.length)
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}