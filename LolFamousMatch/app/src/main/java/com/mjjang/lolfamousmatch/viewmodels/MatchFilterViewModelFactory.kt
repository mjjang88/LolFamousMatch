package com.mjjang.lolfamousmatch.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mjjang.lolfamousmatch.data.MatchTypeRepository

class MatchFilterViewModelFactory(
    private val matchTypeRepository: MatchTypeRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MatchFilterViewModel(matchTypeRepository) as T
    }
}