package com.mjjang.lolfamousmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.activity.addCallback
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mjjang.lolfamousmatch.data.AppDatabase
import com.mjjang.lolfamousmatch.databinding.FragmentMatchFilterBinding
import com.mjjang.lolfamousmatch.manager.App
import com.mjjang.lolfamousmatch.manager.AppPreference
import com.mjjang.lolfamousmatch.utilities.DynamicStyle
import com.mjjang.lolfamousmatch.utilities.InjectorUtils
import com.mjjang.lolfamousmatch.viewmodels.MatchFilterViewModel
import kotlinx.android.synthetic.main.fragment_match_filter.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

    private fun addChip(chipGroup: ChipGroup, name: String, isTitle: Boolean = false) {
        val chip = Chip(activity)
        chip.text = "#${name}"
        DynamicStyle.setChipStyle(chip)
        if (!isTitle) {
            DynamicStyle.setChipFilterStyle(chip)
            chip.setOnCheckedChangeListener(mCheckChangeListener)
        }
        chip.isChecked = checkSelectedChip(name)
        chipGroup.addView(chip)
    }

    private fun delChip(chipGroup: ChipGroup, name: String) {
        for (nIndex in 0 until chipGroup.size) {
            val chip = chipGroup.getChildAt(nIndex) as Chip
            if (chip.text.removePrefix("#") == name) {
                chipGroup.removeViewAt(nIndex)
                return
            }
        }
    }

    private fun checkSelectedChip(name: String) : Boolean {
        return AppPreference.getTagSelected(name) == 1
    }

    private val mCheckChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        val name = buttonView.text.toString().removePrefix("#")
        if (isChecked) {
            // add chip
            addChip(layout_selected, name, true)
            AppPreference.putTagSelected(name, AppPreference.SELECT)
        } else {
            delChip(layout_selected, name)
            AppPreference.putTagSelected(name, AppPreference.NONE)
        }
    }
}