package com.mjjang.lolfamousmatch.data

import androidx.lifecycle.LiveData

class MatchRepository private constructor(
    private val matchDao: MatchDao
){
    fun getMatchList() : LiveData<List<Match>> {
        return matchDao.getMatchList()
    }

    fun getMatch(nID : Int) : LiveData<Match> {
        return matchDao.getMatch(nID)
    }

    companion object {
        @Volatile private var instance: MatchRepository? = null

        fun getInstance(matchDao: MatchDao) =
            instance ?: synchronized(this) {
                instance ?: MatchRepository(matchDao).also { instance = it }
            }
    }
}