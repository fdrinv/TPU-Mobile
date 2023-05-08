package com.fedorinov.tpumobile.data.database.entity

import androidx.room.ColumnInfo
import java.util.UUID

/**
 * Дата-класс для синхронизации с сервером
 */
data class SynchronizeEntity(
    val id: Int,
    @ColumnInfo(name = "external_id")
    val externalId: UUID? = null
)