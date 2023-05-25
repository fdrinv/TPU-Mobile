package com.fedorinov.tpumobile.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Справочник "Студенческие группы".
 * Справочник содержит в себе информацию:
 * - название группы
 * - ссылка на расписание
 * - ссылка на учебный план
 */
@Entity(
    tableName = "group",
    indices = [Index(value = ["external_id"])]
)
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "external_id")
    override val externalId: UUID? = null,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "schedule_url")
    val scheduleUrl: String,

    @ColumnInfo(name = "academic_plan_url")
    val academicPlanUrl: String
) : BaseServerEntity(externalId)
