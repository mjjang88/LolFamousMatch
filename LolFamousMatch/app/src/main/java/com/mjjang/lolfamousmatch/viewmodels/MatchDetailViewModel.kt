package com.mjjang.lolfamousmatch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mjjang.lolfamousmatch.data.Match
import com.mjjang.lolfamousmatch.data.MatchRepository

class MatchDetailViewModel internal constructor(
    matchRepository: MatchRepository,
    strID: String
) : ViewModel() {

    val match: LiveData<Match> = matchRepository.getMatch(strID)
}