package com.fedorinov.tpumobile.logic.service

import com.fedorinov.tpumobile.data.repositories.CommonRepository
import com.fedorinov.tpumobile.data.rest.model.response.LinkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Сервис для работы со ссылками, на данном сервис лежит ответственность с:
 * - хранением в оперативной памяти ссылок.
 * - кеширование ссылок.
 * - запрос ссылок с сервера.
 */
class LinkLoader(private val commonRepository: CommonRepository) {

    // - Создаем собственный scope
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // - Будет хранить в себе все ссылки
    private val links: MutableMap<UUID, MutableSet<LinkResponse>> = mutableMapOf()

    /**
     * Время затраченное на рекурсивный обход дерева составляет ~ 4 ms (на все 68 эле-тов).
     */
    suspend fun loadAll() = scope.launch {
        // - Перед загрузкой следует почистить список
        links.clear()

        val listOfLinks = commonRepository.getLinks()

        listOfLinks.forEach { link ->
            recursiveAdd(link, UUID.fromString(link.id))
        }
    }

    /**
     * Рекурсивный обход дерева в ширину.
     */
    private fun recursiveAdd(link: LinkResponse, parentId: UUID) {
        // - Добавляем узел
        links[parentId]?.add(link) ?: mutableSetOf(link)

        // - Проверяем есть ли у узла дети
        if (link.children.isNullOrEmpty()) return

        // - Если есть пробегаемся по каждому дочернему элементу и вызываем эту же функцию
        link.children.forEach { child ->
            recursiveAdd(child, parentId)
        }
    }

    companion object {
        private val TAG = LinkLoader::class.simpleName
    }

}