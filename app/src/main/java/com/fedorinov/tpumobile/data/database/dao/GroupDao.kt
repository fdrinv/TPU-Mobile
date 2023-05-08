package com.fedorinov.tpumobile.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fedorinov.tpumobile.data.database.entity.GroupEntity
import com.fedorinov.tpumobile.data.database.entity.SynchronizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(group: GroupEntity): Long

    @Query("SELECT id, external_id FROM 'group'")
    suspend fun selectSyncOnce(): List<SynchronizeEntity>

    @Query("SELECT * FROM 'group' ORDER BY name")
    fun selectAllAsFlow(): Flow<List<GroupEntity>>

    @Update
    suspend fun update(group: GroupEntity)

    @Query("DELETE FROM 'group' WHERE id=:id")
    suspend fun deleteById(id: Int)

    @Delete
    suspend fun delete(group: GroupEntity)

}