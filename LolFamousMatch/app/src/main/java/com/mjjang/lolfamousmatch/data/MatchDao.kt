package com.mjjang.lolfamousmatch.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface MatchDao {
    @Query("SELECT * FROM matchs ORDER BY id asc")
    fun getMatchList(): LiveData<List<Match>>
}