package com.fedorinov.tpumobile.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fedorinov.tpumobile.data.database.entity.GroupEntity
import com.fedorinov.tpumobile.data.database.entity.MenuItemEntity
import com.fedorinov.tpumobile.data.database.entity.SynchronizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(menuItem: MenuItemEntity): Long

    @Query("SELECT id, external_id FROM 'menu_item'")
    suspend fun selectSyncOnce(): List<SynchronizeEntity>

    @Query("SELECT * FROM 'menu_item'")
    suspend fun selectAllOnce(): List<MenuItemEntity>

    @Query("SELECT * FROM 'menu_item' ORDER BY position")
    fun selectAllAsFlow(): Flow<List<MenuItemEntity>>

    @Update
    suspend fun update(group: MenuItemEntity)

    @Query("DELETE FROM 'menu_item' WHERE id=:id")
    suspend fun deleteById(id: Int)

    @Delete
    suspend fun delete(group: MenuItemEntity)

}