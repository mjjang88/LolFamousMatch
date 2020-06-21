package com.mjjang.lolfamousmatch.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchDao {
    @Query("SELECT * FROM matchs ORDER BY id asc")
    fun getMatchList(): LiveData<List<Match>>

    @Query("SELECT * FROM matchs WHERE id = :id")
    fun getMatch(id : Int): LiveData<Match>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matchs: List<Match>)
}