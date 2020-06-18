package com.mjjang.lolfamousmatch.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.mjjang.lolfamousmatch.data.AppDatabase
import com.mjjang.lolfamousmatch.data.MatchRepository
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
}