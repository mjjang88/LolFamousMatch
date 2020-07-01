package com.mjjang.lolfamousmatch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mjjang.lolfamousmatch.data.MatchType
import com.mjjang.lolfamousmatch.data.MatchTypeRepository

class MatchFilterViewModel internal constructor(
    matchFilterRepository: MatchTypeRepository
) : ViewModel(){
    val filters: LiveData<List<MatchType>> = matchFilterRepository.getMatchTypeList()
}