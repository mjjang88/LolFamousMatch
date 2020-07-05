package com.mjjang.lolfamousmatch.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MatchTypeDao {
    @Query("SELECT * FROM matchtype ORDER BY select_count desc")
    fun getMatchTypeList(): LiveData<List<MatchType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matchType: List<MatchType>)

    @Query("DELETE FROM matchtype")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAndInsert(matchType: List<MatchType>) {
        deleteAll()
        insertAll(matchType)
    }
}