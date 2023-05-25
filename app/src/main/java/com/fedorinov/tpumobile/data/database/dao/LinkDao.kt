package com.fedorinov.tpumobile.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fedorinov.tpumobile.data.database.entity.LinkEntity
import com.fedorinov.tpumobile.data.database.entity.SynchronizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(menuItem: LinkEntity): Long

    @Query("SELECT id, external_id FROM 'link'")
    suspend fun selectSyncOnce(): List<SynchronizeEntity>

    @Query("SELECT * FROM 'link'")
    suspend fun selectAllOnce(): List<LinkEntity>

    @Query("SELECT * FROM 'link' ORDER BY position")
    fun selectAllAsFlow(): Flow<List<LinkEntity>>

    @Update
    suspend fun update(group: LinkEntity)

    @Query("DELETE FROM 'link' WHERE id=:id")
    suspend fun deleteById(id: Int)

    @Delete
    suspend fun delete(group: LinkEntity)

}