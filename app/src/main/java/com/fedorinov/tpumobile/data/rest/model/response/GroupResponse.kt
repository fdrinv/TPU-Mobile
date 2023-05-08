package com.fedorinov.tpumobile.data.rest.model.response

import com.fedorinov.tpumobile.data.database.entity.GroupEntity
import com.google.gson.annotations.SerializedName
import java.util.UUID

/**
 * Ответ учебных групп.
 * Пример:
 * {
 *   "id": "71848C7E-F081-4C8E-BCCC-D8626E733C5D",
 *   "name": "15Б11",
 *   "scheduleUrl": "https://rasp.tpu.ru/gruppa_37791",
 *   "academicPlanUrl": "https://up.tpu.ru/view/detali.html?id=25931"
 * },
 *
 */
data class GroupResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("scheduleUrl") val scheduleUrl: String,
    @SerializedName("academicPlanUrl") val academicPlanUrl: String
) {
    /**
     * Преобразовать объект в Entity.
     * @param withId - известный локальный идентификатор или null
     * @return - Entity
     */
    fun toEntity(
        withId: Int? = null,
    ) = GroupEntity(
        id = withId ?: 0,
        externalId = UUID.fromString(id),
        name = name,
        scheduleUrl = scheduleUrl,
        academicPlanUrl = academicPlanUrl
    )
}
