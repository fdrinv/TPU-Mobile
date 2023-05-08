package com.fedorinov.tpumobile.ui.start.reg

import java.util.Locale

enum class Language {
    RUSSIAN,
    ENGLISH;

    companion object {
        fun getLanguage(): Language {
            val locale = Locale.getDefault()
            return if (locale.equals(Locale("ru", "RU"))) RUSSIAN
            else ENGLISH
        }
    }
}