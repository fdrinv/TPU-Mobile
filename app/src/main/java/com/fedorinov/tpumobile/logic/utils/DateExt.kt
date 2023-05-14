package com.fedorinov.tpumobile.logic.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val DATE_PATTERN = "HH:mm:ss dd.MM.yyyy"

const val EVENT_DATE_PATTERN = "d MMM yyyy"

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}