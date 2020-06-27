package com.mjjang.lolfamousmatch.data

import androidx.lifecycle.LiveData

class MatchTypeRepository private constructor(
    private val matchTypeDao: MatchTypeDao
){
    fun getMatchTypeList() : LiveData<List<MatchType>> {
        return matchTypeDao.getMatchTypeList()
    }

    companion object {
        @Volatile private var instance: MatchTypeRepository? = null

        fun getInstance(matchTypeDao: MatchTypeDao) =
            instance ?: synchronized(this) {
                instance ?: MatchTypeRepository(matchTypeDao).also { instance = it }
            }
    }
}