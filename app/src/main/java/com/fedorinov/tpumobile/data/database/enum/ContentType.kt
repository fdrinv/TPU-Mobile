package com.fedorinov.tpumobile.data.database.enum

/**
 * Типы пунктов меню:
 * [LINKS_LIST] - список ссылок.
 * [ARTICLE] - статья.
 * [LINK] - ссылка.
 */
enum class ContentType(val id: Int) {
    LINKS_LIST(1),
    ARTICLE(2),
    LINK(3);

    companion object {
        fun fromName(name: String): ContentType? = values().find { it.name == name }
    }
}