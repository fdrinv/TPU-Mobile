package com.fedorinov.tpumobile.data.database.enum

import com.fedorinov.tpumobile.App
import com.fedorinov.tpumobile.R

/**
 * Типы пунктов меню:
 * [LINKS_LIST] - список ссылок.
 * [ARTICLE] - статья.
 * [LINK] - ссылка.
 */
enum class ContentType(val id: Int, val desc: String) {
    LINKS_LIST(1, App.getAppResources()?.getString(R.string.enum_content_type_links_list) ?: "Список"),
    ARTICLE(2, App.getAppResources()?.getString(R.string.enum_content_type_article) ?: "Статья"),
    LINK(3, App.getAppResources()?.getString(R.string.enum_content_type_link) ?: "Внешняя ссылка"),
    SCHEDULE(4, App.getAppResources()?.getString(R.string.enum_content_type_schedule) ?: "Расписание");

    companion object {
        fun fromName(name: String): ContentType? = values().find { it.name == name }
    }
}