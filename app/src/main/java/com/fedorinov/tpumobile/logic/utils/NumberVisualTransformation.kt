package com.fedorinov.tpumobile.logic.utils

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

private const val RUSSIAN_NUMBER_MASK = "+7 (XXX) XXX-XX-XX"

fun mobileNumberFilter(text: AnnotatedString): TransformedText {
    // - Обрезаем номер, если число символов выходит за границу
    val trimmed =
        if (text.text.length >= 10) text.text.substring(0..9) else text.text

    val annotatedString = buildAnnotatedString {
        append("+7 (")
        trimmed.forEachIndexed { ix, char ->
            // - Если символ находится на позиции 3: "+7 (XXX"
            if (ix == 3) append(") ")
            // - Если символ находится на позиции 6 и 8: "+7 (XXX) XXX" и "+7 (XXX) XXX-XX"
            if (ix == 6 || ix == 8)  append("-")
            append(char)
        }
        pushStyle(SpanStyle(color = Color(0xFF72796F)))
        append(RUSSIAN_NUMBER_MASK.takeLast(RUSSIAN_NUMBER_MASK.length - length))
    }

    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return if (offset <= 3) offset + 4
            else if (offset <= 5) offset + 6
            else if (offset <= 8) offset + 7
            else if (offset <= 10) offset + 8
            else 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            Log.i("TEST", "offset $offset")
            return if (offset <= 3) offset
            else 0
        }
    }

    return TransformedText(annotatedString, phoneNumberOffsetTranslator)
}