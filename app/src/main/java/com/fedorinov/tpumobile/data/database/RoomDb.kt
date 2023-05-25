package com.fedorinov.tpumobile.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fedorinov.tpumobile.data.database.dao.GroupDao
import com.fedorinov.tpumobile.data.database.dao.LinkDao
import com.fedorinov.tpumobile.data.database.entity.GroupEntity
import com.fedorinov.tpumobile.data.database.entity.LinkEntity
import kotlinx.coroutines.CoroutineScope

// - Название файла базы данных
private const val DB_FILE = "tpu_mobile_database"
// - Версия базы данных
private const val DB_VERSION = 1

@Database(
    version = DB_VERSION,
    exportSchema = true,
    entities = [
        GroupEntity::class,
        LinkEntity::class
    ]
)
abstract class RoomDb : RoomDatabase() {

    abstract fun groupDao() : GroupDao
    abstract fun linkDao() : LinkDao

    companion object {
        // - Конструктор подключения к бд
        fun databaseBuilder(appContext: Context, scope: CoroutineScope): RoomDb {
            val preDb = Room.databaseBuilder(appContext, RoomDb::class.java, DB_FILE)

            return preDb.build()
        }
    }

}