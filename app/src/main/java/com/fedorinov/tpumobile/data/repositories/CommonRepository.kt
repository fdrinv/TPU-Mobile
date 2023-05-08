package com.fedorinov.tpumobile.data.repositories

import com.fedorinov.tpumobile.data.database.dao.GroupDao

/**
 * Содержит в себе общие методы, такие как получение полных списков чего-лио.
 */
class CommonRepository(private val groupDao: GroupDao) {

    /**
     * Получаем поток данных для наблюдения за группами
     */
    fun getAllGroupsAsFlow() = groupDao.selectAllAsFlow()

}