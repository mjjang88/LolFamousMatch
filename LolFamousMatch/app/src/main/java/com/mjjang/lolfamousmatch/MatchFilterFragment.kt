package com.mjjang.lolfamousmatch

import android.os.Bundle
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mjjang.lolfamousmatch.databinding.FragmentMatchFilterBinding
import com.mjjang.lolfamousmatch.utilities.DynamicStyle
import com.mjjang.lolfamousmatch.utilities.InjectorUtils
import com.mjjang.lolfamousmatch.viewmodels.MatchFilterViewModel

class MatchFilterFragment : Fragment() {

    private val matchFilterViewModel: MatchFilterViewModel by viewModels {
        InjectorUtils.provideMatchFilterViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMatchFilterBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        context ?: return binding.root

        activity?.onBackPressedDispatcher?.addCallback {
            view?.findNavController()?.navigateUp()
        }

        matchFilterViewModel.filters.observe(viewLifecycleOwner, Observer {

            // team filter
            for (filter in it) {
                when (filter.category) {
                    "팀" -> addChip(binding.layoutTeam, filter.name)
                    "경기유형" -> addChip(binding.layoutMatchType, filter.name)
                    "라인" -> addChip(binding.layoutLane, filter.name)
                    "플레이어" -> addChip(binding.layoutPlayer, filter.name)
                    "챔피언" -> addChip(binding.layoutChampion, filter.name)
                }
            }

        })

        return binding.root
    }

    private fun addChip(chipGroup: ChipGroup, name: String) {
        val chip = Chip(activity)
        chip.text = "#${name}"
        DynamicStyle.setChipStyle(chip)
        DynamicStyle.setChipFilterStyle(chip)
        chipGroup.addView(chip)
    }
}