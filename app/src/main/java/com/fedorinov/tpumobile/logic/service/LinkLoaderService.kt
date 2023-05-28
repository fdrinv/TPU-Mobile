package com.fedorinov.tpumobile.logic.service

import android.util.Log
import com.fedorinov.tpumobile.data.repositories.AuthRepository
import com.fedorinov.tpumobile.data.rest.RestApiTpu
import com.fedorinov.tpumobile.data.rest.model.response.LinkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.UUID

/**
 * Сервис для работы со ссылками, на данном сервис лежит ответственность с:
 * - хранением в оперативной памяти ссылок.
 * - кеширование ссылок.
 * - запрос ссылок с сервера.
 */
class LinkLoaderService(
    private val restApi: RestApiTpu,
    private val authRepository: AuthRepository
) {

    // - Будет хранить в себе все ссылки
    private val _links: MutableStateFlow<Set<LinkResponse>> = MutableStateFlow(setOf())
    val links: StateFlow<Set<LinkResponse>> = _links
    // - Хранит в себе только ссылки бокового меню
    private val _linksById: MutableStateFlow<List<LinkResponse>> = MutableStateFlow(listOf())
    val linksById: StateFlow<List<LinkResponse>> = _linksById
    // - Запрос с сервера
    private val response: MutableStateFlow<List<LinkResponse>> = MutableStateFlow(emptyList())

    val mapOfLinks: MutableStateFlow<Map<UUID, LinkResponse>> = MutableStateFlow(mapOf())

    init {
        // - Запускаем корутину на потоке IO
        CoroutineScope(Dispatchers.IO).launch {
            response.updateAndGet {
                restApi.api.getLinks(
                    token = "Bearer ${authRepository.userPreferencesFlow.firstOrNull()?.userToken}",
                    language = Locale.getDefault().toString(),
                    languageId = authRepository.userPreferencesFlow.first().languageId,
                    email = authRepository.userPreferencesFlow.first().email
                ).body() ?: emptyList()
            }
        }
    }

    /**
     * Время затраченное на рекурсивный обход дерева составляет ~ 4 ms (на все 68 эле-тов).
     */
    suspend fun loadAll() = withContext(Dispatchers.IO) {
        // - Перед загрузкой следует почистить список
        _links.update { setOf() }

        response.value.forEach { link ->
            recursiveAdd(link)
        }
    }

    suspend fun loadAllMap() = withContext(Dispatchers.IO) {

        Log.i(TAG, "-----------------------------------------------------")
        Log.i(TAG, "Запущен рекурсивный алгоритм для заполнения карты")
        Log.i(TAG, "mapOfLinks.entries.size = ${mapOfLinks.value.entries.size}")
        Log.i(TAG, "mapOfLinks.values.size = ${mapOfLinks.value.values.size}")
        Log.i(TAG, "-----------------------------------------------------")

        mapOfLinks.update { mapOf() }

        response.value.forEach { link ->
            mapOfLinks.update { prevMap ->
                val newMap = prevMap.toMutableMap()
                newMap[UUID.fromString(link.id)] = link
                newMap
            }
            recursiveAddMap(link)
        }

        Log.i(TAG, "-----------------------------------------------------")
        Log.i(TAG, "Завершен рекурсивный алгоритм для заполнения карты")
        Log.i(TAG, "mapOfLinks.entries.size = ${mapOfLinks.value.entries.size}")
        Log.i(TAG, "mapOfLinks.values.size = ${mapOfLinks.value.values.size}")
        Log.i(TAG, "-----------------------------------------------------")

    }

    /**
     * Получить ссылку и ее дочерние ссылки.
     * @param id - UUID ссылки.
     */
    suspend fun loadById(id: UUID) = withContext(Dispatchers.IO) {
        // - Перед загрузкой следует почистить список
        _linksById.update { listOf() }

        response.value.find { UUID.fromString(it.id) == id }?.let { link -> recursiveAdd(link) }
    }

    /**
     * Рекурсивный обход дерева в ширину.
     */
    private fun recursiveAdd(response: LinkResponse) {
        // - Добавляем узел
        _links.update { prevList ->
            val newList = prevList.toMutableSet()
            newList.add(response)
            newList
        }
        // - Проверяем есть ли у узла дети
        if (response.children.isNullOrEmpty()) return
        // - Если есть пробегаемся по каждому дочернему элементу и вызываем эту же функцию
        response.children.forEach { child ->
            recursiveAdd(child)
        }
    }

    private fun recursiveAddMap(response: LinkResponse) {
        // - Добавляем узел
        mapOfLinks.update { prevMap ->
            val newMap = prevMap.toMutableMap()
            newMap[UUID.fromString(response.id)] = response
            newMap
        }
        // - Проверяем есть ли у узла дети
        if (response.children.isNullOrEmpty()) return
        // - Если есть пробегаемся по каждому дочернему элементу и вызываем эту же функцию
        response.children.forEach { child ->
            recursiveAdd(child)
        }
    }

    companion object {
        private val TAG = LinkLoaderService::class.simpleName
    }

}