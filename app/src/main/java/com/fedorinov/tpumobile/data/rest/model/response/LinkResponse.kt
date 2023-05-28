package com.fedorinov.tpumobile.data.rest.model.response

import com.fedorinov.tpumobile.data.database.entity.LinkEntity
import com.fedorinov.tpumobile.data.database.enum.ContentType
import com.fedorinov.tpumobile.ui.model.LinkItem
import com.google.gson.annotations.SerializedName
import java.util.UUID

/**
 * Ответ пунктов меню.
 * Пример:
 *   "id": "F5956F34-6E09-4CBB-AD24-641870A76059",
 *   "name": "О ТПУ",
 *   "level": 1,
 *   "position": 1,
 *   "type": "LINKS_LIST",
 *   "image": "https://internationals.tpu.ru:8080/api/media/img/E5EC1D87-4E13-4578-B6D9-ED46AF3089F2",
 *   "imageId": "E5EC1D87-4E13-4578-B6D9-ED46AF3089F2",
 *   "children": [
 *       {
 *       "id": "C32B8AEC-E856-4E8C-AAA4-21C4C0159A8C",
 *       "name": "Томский политехнический университет",
 *       "level": 2,
 *       "position": 1,
 *       "type": "ARTICLE",
 *       "idArticle": "B4A5DBA4-DDA8-47F4-8C59-2D6ACD3B948B",
 *       "image": "https://internationals.tpu.ru:8080/api/media/img/0EF1D11C-0A77-4A96-B009-1956D385D8ED",
 *       "imageId": "0EF1D11C-0A77-4A96-B009-1956D385D8ED"
 *       },
 */
data class LinkResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("level") val level: Int,
    @SerializedName("position") val position: Int,
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String? = null,
    @SerializedName("idArticle") val articleId: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("imageId") val imageId: String? = null,
    @SerializedName("children") val children: List<LinkResponse>? = null,
) {
    /**
     * Преобразовать объект в Entity.
     * @param withId - известный локальный идентификатор или null
     * @return - Entity
     */
    fun toEntity(
        withId: Int? = null,
        articleIdMap: Map<UUID, Int>,
        imageIdMap: Map<UUID, Int>
    ): LinkEntity {
        val localArticleId: Int? = if (articleId != null) articleIdMap[UUID.fromString(articleId)] else null
        val localImageId: Int? = if (imageId != null) imageIdMap[UUID.fromString(imageId)] else null
        return LinkEntity(
            id = withId ?: 0,
            externalId = UUID.fromString(id),
            name = name,
            level = level,
            position = position,
            type = type,
            url = url,
            articleId = localArticleId,
            image = image,
            imageId = localImageId
        )
    }

    fun toLinkItem() = LinkItem(
        id = UUID.fromString(id),
        name = name,
        level = level,
        type = ContentType.fromName(type),
        position = position,
        imgUrl = image
    )
}
