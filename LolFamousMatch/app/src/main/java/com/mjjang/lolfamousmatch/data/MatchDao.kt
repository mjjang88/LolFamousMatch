package com.mjjang.lolfamousmatch.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MatchDao {
    @Query("SELECT * FROM matchs")
    fun getMatchList(): LiveData<List<Match>>

    @Query("SELECT * FROM matchs WHERE id = :id")
    fun getMatch(id : String): LiveData<Match>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matchs: List<Match>)

    @Query("DELETE FROM matchs")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAndInsert(matchs: List<Match>) {
        deleteAll()
        insertAll(matchs)
    }
}