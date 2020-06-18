package com.mjjang.lolfamousmatch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mjjang.lolfamousmatch.data.Match
import com.mjjang.lolfamousmatch.data.MatchRepository

class MatchListViewModel internal constructor(
    matchRepository: MatchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val matchs: LiveData<List<Match>> = matchRepository.getMatchList()
}