package com.mjjang.lolfamousmatch.data

import androidx.lifecycle.LiveData

class MatchRepository private constructor(
    private val matchDao: MatchDao
){
    fun getMatchList() : LiveData<List<Match>> {
        return matchDao.getMatchList()
    }

    fun getMatch(strID : String) : LiveData<Match> {
        return matchDao.getMatch(strID)
    }

    companion object {
        @Volatile private var instance: MatchRepository? = null

        fun getInstance(matchDao: MatchDao) =
            instance ?: synchronized(this) {
                instance ?: MatchRepository(matchDao).also { instance = it }
            }
    }
}