package com.mjjang.lolfamousmatch.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.mjjang.lolfamousmatch.data.AppDatabase
import com.mjjang.lolfamousmatch.data.MatchRepository
import com.mjjang.lolfamousmatch.data.MatchTypeRepository
import com.mjjang.lolfamousmatch.viewmodels.MatchDetailViewModelFactory
import com.mjjang.lolfamousmatch.viewmodels.MatchFilterViewModelFactory
import com.mjjang.lolfamousmatch.viewmodels.MatchListViewModelFactory

object InjectorUtils {

    private fun getMatchRepository(context: Context): MatchRepository {
        return MatchRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).matchDao()
        )
    }

    fun provideMatchListViewModelFactory(fragment: Fragment): MatchListViewModelFactory {
        val repository = getMatchRepository(fragment.requireContext())
        return MatchListViewModelFactory(repository, fragment)
    }

    fun provideMatchDetailViewModelFactory(fragment: Fragment, nID: Int): MatchDetailViewModelFactory {
        val repository = getMatchRepository(fragment.requireContext())
        return MatchDetailViewModelFactory(repository, nID)
    }

    private fun getMatchTypeRepository(context: Context): MatchTypeRepository {
        return MatchTypeRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).matchTypeDao()
        )
    }

    fun provideMatchFilterViewModelFactory(fragment: Fragment): MatchFilterViewModelFactory {
        val repository = getMatchTypeRepository(fragment.requireContext())
        return MatchFilterViewModelFactory(repository)
    }
}