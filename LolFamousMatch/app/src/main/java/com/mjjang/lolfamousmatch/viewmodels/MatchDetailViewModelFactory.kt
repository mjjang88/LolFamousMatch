package com.mjjang.lolfamousmatch.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mjjang.lolfamousmatch.data.MatchRepository

class MatchDetailViewModelFactory(
    private val matchRepository: MatchRepository,
    private val strID: String
) :  ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MatchDetailViewModel(matchRepository, strID) as T
    }
}