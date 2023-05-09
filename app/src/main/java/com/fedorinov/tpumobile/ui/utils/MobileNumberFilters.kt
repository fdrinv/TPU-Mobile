package com.fedorinov.tpumobile.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import com.fedorinov.tpumobile.ui.start.reg.Language

private const val NUMBER_RUSSIAN_MASK = "+X (XXX) XXX-XX-XX"

/*fun mobileNumberFilter(text: AnnotatedString): TransformedText {
    // Подрезаем номер, если он выходит за границы
    val trimmed = when(Language.getLanguage()) {
        Language.RUSSIAN -> {
            if (text.text.length >= 18) text.text.substring(0..17) else text.text
        }
        Language.ENGLISH -> { text }
    }
    val resultNumberString = buildString {
        append("+7 (")
        text.forEachIndexed { ix, number ->
            if (ix == 6) append(") ")
            else if (ix == 11 || ix == 14) append("-")
        }
    }

    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            when(offset) {
                0 -> offset + 4
                in (3..6) -> offset + 2
                in (7 .. 9) ->

            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 4) return offset - 1
            if (offset <= 6) return offset - 2
            if (offset <= 9) return offset - 3
            return 9
        }
    }

}*/
