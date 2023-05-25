package com.fedorinov.tpumobile.ui.start.reg

import com.fedorinov.tpumobile.App
import com.fedorinov.tpumobile.R
import java.util.Locale

enum class Language(val id: Int, val customName: String) {
    RUSSIAN(1, App.getAppResources()?.getString(R.string.enum_russian) ?: "Russian"),
    ENGLISH(2, App.getAppResources()?.getString(R.string.enum_english) ?: "English");

    companion object {
        fun getLanguage(): Language {
            val locale = Locale.getDefault()
            return if (locale.equals(Locale("ru", "RU"))) RUSSIAN
            else ENGLISH
        }
    }
}