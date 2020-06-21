package com.mjjang.lolfamousmatch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mjjang.lolfamousmatch.data.Match
import com.mjjang.lolfamousmatch.data.MatchRepository

class MatchDetailViewModel internal constructor(
    matchRepository: MatchRepository,
    nID: Int
) : ViewModel() {

    val match: LiveData<Match> = matchRepository.getMatch(nID)
}