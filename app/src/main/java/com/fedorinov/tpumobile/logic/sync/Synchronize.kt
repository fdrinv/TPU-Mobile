package com.fedorinov.tpumobile.logic.sync

import android.util.Log
import com.fedorinov.tpumobile.data.database.RoomDb
import com.fedorinov.tpumobile.data.database.dao.GroupDao
import com.fedorinov.tpumobile.data.database.dao.LinkDao
import com.fedorinov.tpumobile.data.database.entity.GroupEntity
import com.fedorinov.tpumobile.data.database.entity.LinkEntity
import com.fedorinov.tpumobile.data.database.entity.SynchronizeEntity
import com.fedorinov.tpumobile.data.repositories.AuthRepository
import com.fedorinov.tpumobile.data.rest.RestApiTpu
import com.fedorinov.tpumobile.data.rest.model.response.GroupResponse
import com.fedorinov.tpumobile.data.rest.model.response.LinkResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import java.util.Locale
import java.util.UUID

/**
 * Класс синхронизации с сервером.
 */
class Synchronize(
    private val db: RoomDb,
    private val restApi: RestApiTpu,

    private val authRepository: AuthRepository,

    private val groupDao: GroupDao,
    private val linkDao: LinkDao
) {

    //region План синхронизации
    // - Учебные группы
    private val syncGroups = SyncInfoItem(
        id = 1,
        name = "Учебные группы",
        estimatedSize = 10
    )
    // - Пункты главного меню
    private val syncMenuItems = SyncInfoItem(
        id = 2,
        name = "Пункты меню",
        estimatedSize = 60
    )
    // - Список на синхронизацию
    private val syncListDictionaries = listOf(
        syncGroups,  // Независим (среди первых)
        syncMenuItems
    )
    //endregion

    /**
     * Общая функция синхронизации.
     */
    suspend fun doSync() {
        Log.i(TAG, "doSync: is started!")
        // - externalId - выступает в роли ключа (так как он будет уникальным для каждой записи)
        // - id - выступает в роли значения (уникальный идентификатор записи)
        val groups: Map<UUID, Int> = groupDao.selectAllOnce().associate { it.externalId!! to it.id }
        val menuItems: Map<UUID, Int> = linkDao.selectAllOnce().associate { it.externalId!! to it.id }

        try {
            Log.i(TAG, "doSync: start syncing...")

            syncListDictionaries.forEach { item ->
                Log.i(TAG, "doSync: item = ${item.name}")

                when(item) {
                    syncGroups -> syncGroups()
                    syncMenuItems -> syncLinks()
                }
            }

        } finally {
            Log.i(TAG, "doSync: stop.")
        }

        Log.i(TAG, "doSync: done.")
    }

    /**
     * Загрузка учебных групп.
     */
    private suspend fun syncGroups() {
        val dao = db.groupDao()
        sync<GroupResponse, GroupEntity, SynchronizeEntity>(
            doRestRequest = {
               restApi.api.getGroups(Locale.getDefault().toString())
            },
            doDbSelect = {
                dao.selectSyncOnce()
            },
            doCompare = { dbRec, restRec ->
                dbRec.externalId == UUID.fromString(restRec.id)
            },
            doConvertToEntity = { restRec, syncRec ->
                restRec.toEntity(withId = syncRec?.id)
            },
            doUpdateOrInsert = { dbRec, _, isUpdate ->
                val localId = if (isUpdate) {
                    dao.update(dbRec)
                    dbRec.id
                } else {
                    dao.insert(dbRec).toInt()
                }
                // groups[dbRec.externalId!!] = localId
            },
            doDelete = { dbRec ->
                dao.deleteById(dbRec.id)
            }
        )
    }

    /**
     * Загрузка пунктов меню.
     */
    private suspend fun syncLinks() {
        val dao = db.linkDao()
        sync<LinkResponse, LinkEntity, SynchronizeEntity>(
            doRestRequest = {
                restApi.api.getLinks(
                    token = "Bearer ${authRepository.userPreferencesFlow.firstOrNull()?.userToken}",
                    language = Locale.getDefault().toString(),
                    languageId = authRepository.userPreferencesFlow.first().languageId,
                    email = authRepository.userPreferencesFlow.first().email
                )
            },
            doDbSelect = {
                dao.selectSyncOnce()
            },
            doCompare = { dbRec, restRec ->
                dbRec.externalId == UUID.fromString(restRec.id)
            },
            doConvertToEntity = { restRec, syncRec ->
                restRec.toEntity(
                    withId = syncRec?.id,
                    articleIdMap = mapOf(),
                    imageIdMap = mapOf()
                )
            },
            doUpdateOrInsert = { dbRec, restRec, isUpdate ->
                // - Получаем локальный идентификатор вставленной сущности
                val localId = if (isUpdate) {
                    dao.update(dbRec)
                    dbRec.id
                } else {
                    dao.insert(dbRec).toInt()
                }

                Log.i(TAG, "-------------------------------------")
                Log.i(TAG, "Рекурсивный алгоритм начал работу!")
                Log.i(TAG, "Локальный идентификатор сущности родителя localId = $localId")
                Log.i(TAG, "Объект рекурсивного прохождения restRec = $restRec")
                Log.i(TAG, "-------------------------------------")

            },
            doDelete = { dbRec ->
                dao.deleteById(dbRec.id)
            }
        )
    }

    var numberOfIteration = 0L

    private suspend fun recursiveInsertChildren(parentLocalId: Int, localId: Int, restRec: LinkResponse, dbRec: LinkEntity) {

        numberOfIteration++
        Log.i(TAG, "Рекурсивный проход по дереву номер $numberOfIteration")


    }

    /**
     * Синхронизация справочника от сервера.
     * Новые записи добавляются, совпадающие по UUID обновляются, иные - удаляются из БД.
     * @param TR - Тип записи от сервера
     * @param TD - Тип записи БД
     * @param TC - Тип короткой запись БД для сравнения
     */
    private suspend fun <TR, TD, TC> sync(
        doRestRequest: suspend () -> Response<List<TR>>,
        doDbSelect: suspend () -> List<TC>,
        doCompare: (TC, TR) -> Boolean,
        doConvertToEntity: suspend (TR, TC?) -> TD?,
        doUpdateOrInsert: suspend (TD, TR, Boolean) -> Unit,
        doDelete: suspend (TC) -> Unit,
        updateSyncInfo: (suspend (status: SyncInfoItemState) -> Unit)? = null
    ) {
        // Получаем список всех записей на сервере
        val recordsFromServer: List<TR> = restApi.checkResponse(doRestRequest())
        Log.i(TAG, "recordsFromServer.size: ${recordsFromServer.size}")

        // Запросим весь список из БД (он не должен быть гигантским, влезет в память)
        val recordsFromDb: MutableList<TC> = doDbSelect().toMutableList()

        // Получим объём работы
        val totalRecCount = recordsFromServer.size

        // По всем записям от сервера
        recordsFromServer.forEachIndexed { index, serverRec ->
            // Найти пару из БД.
            // Пробегаем по все записям в таблице и сравниваем с записью с сервера.
            // dbRecord будет равен null, если такой записи с сервера еще нет в локальной бд
            val dbRec: TC? = recordsFromDb.find { doCompare(it, serverRec) }

            // Преобразуем в запись для БД
            val entity = doConvertToEntity(serverRec, dbRec)

            // Если преобразование удалось - сохраним запись в БД
            if (entity != null) {
                // Вставляем или обновляем запись
                doUpdateOrInsert(entity, serverRec, dbRec != null)
            }

            // Удаляем использованную запись
            if (dbRec != null) recordsFromDb.remove(dbRec)

            // Сообщим состояние работы
            if (updateSyncInfo != null) updateSyncInfo(
                SyncInfoItemState.Processing
            )
        }
    }

    companion object {
        private val TAG = Synchronize::class.simpleName
    }

}