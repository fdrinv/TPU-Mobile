package com.fedorinov.tpumobile.data.repositories

import com.fedorinov.tpumobile.data.database.dao.GroupDao
import com.fedorinov.tpumobile.data.database.enum.ContentType
import com.fedorinov.tpumobile.data.rest.RestApiTpu
import com.fedorinov.tpumobile.data.rest.model.response.LinkResponse
import com.fedorinov.tpumobile.ui.model.LinkItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.util.Locale
import java.util.UUID

/**
 * Содержит в себе общие методы, такие как получение полных списков чего-лио.
 */
class CommonRepository(
    private val restApiTpu: RestApiTpu,
    private val authRepository: AuthRepository,
    private val groupDao: GroupDao
) {

    /**
     * Получаем поток данных для наблюдения за группами
     */
    fun getAllGroupsAsFlow() = groupDao.selectAllAsFlow()

    /**
     * Получаем список всех ссылок используемые в приложение.
     */
    suspend fun getLinks(): List<LinkResponse> =
        restApiTpu.api.getLinks(
            token = "Bearer ${authRepository.userPreferencesFlow.firstOrNull()?.userToken}",
            language = Locale.getDefault().toString(),
            languageId = authRepository.userPreferencesFlow.first().languageId,
            email = authRepository.userPreferencesFlow.first().email
        ).body() ?: emptyList()
}