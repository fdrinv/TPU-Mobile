package com.fedorinov.tpumobile.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Сущность "Пункт меню".
 * Сущность содержит в себе информацию:
 * - название пункта
 * - уровень пункта в дереве
 * - позиция в списке
 * - тип пункта
 * - ссылка на пункт
 * - ссылка на фотографию
 * - идентификатор фотографии в системе
 * - * список детей (подпунктов)
 */
@Entity(
    tableName = "menu_item",
    indices = [
        Index(value = ["external_id"]),
        Index(value = ["article_id"]),
        Index(value = ["image_id"]),
    ]
)
data class MenuItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "external_id")
    override val externalId: UUID? = null,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "level")
    val level: Int,

    @ColumnInfo(name = "position")
    val position: Int,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "url")
    val url: String? = null,

    @ColumnInfo(name = "article_id")
    val articleId: Int? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,

    @ColumnInfo(name = "image_id")
    val imageId: Int? = null,
) : BaseServerEntity(externalId)
