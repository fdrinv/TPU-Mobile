package com.fedorinov.tpumobile.logic.utils

fun String.checkLastCharForRegex(regex: Regex): Boolean {
    //  Если строка не пустая, проверим последний символ на регулярное выражение
    return if (this.isNotEmpty()) regex matches this.last().toString()
    else true
}