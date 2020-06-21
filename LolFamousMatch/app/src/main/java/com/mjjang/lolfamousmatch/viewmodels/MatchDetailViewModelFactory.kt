package com.mjjang.lolfamousmatch.viewmodels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.mjjang.lolfamousmatch.data.MatchRepository

class MatchDetailViewModelFactory(
    private val matchRepository: MatchRepository,
    private val nID: Int
) :  ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MatchDetailViewModel(matchRepository, nID) as T
    }
}