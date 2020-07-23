package com.mjjang.lolfamousmatch

import android.content.Intent
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
import com.mjjang.lolfamousmatch.databinding.FragmentMatchFilterBinding
import com.mjjang.lolfamousmatch.firestore.FireStoreProc
import com.mjjang.lolfamousmatch.manager.AppPreference
import com.mjjang.lolfamousmatch.utilities.DynamicStyle
import com.mjjang.lolfamousmatch.utilities.InjectorUtils
import com.mjjang.lolfamousmatch.viewmodels.MatchFilterViewModel
import kotlinx.android.synthetic.main.fragment_match_filter.*

class MatchFilterFragment : Fragment() {

    private val matchFilterViewModel: MatchFilterViewModel by viewModels {
        InjectorUtils.provideMatchFilterViewModelFactory(this)
    }

    var mbFilterChanged = false

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
            if (mbFilterChanged) {
                FireStoreProc.getMatchList(AppPreference.getTagSelectedAll())
            }
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
        if (isTitle) {
            chip.isCloseIconVisible = true
            chip.closeIcon = resources.getDrawable(R.drawable.ic_baseline_close_24, null)
            chip.setOnCloseIconClickListener {
                AppPreference.putTagSelected(name, AppPreference.NONE)
                (it.parent as ViewGroup).removeView(it)
                setFilterChipChecked(name)
                mbFilterChanged = true
            }
        } else {
            DynamicStyle.setChipFilterStyle(chip)
            chip.isChecked = checkSelectedChip(name)
            if (chip.isChecked) {
                addChip(layout_selected, name, true)
            }
            chip.setOnCheckedChangeListener(mCheckChangeListener)
        }
        chipGroup.addView(chip)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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

        mbFilterChanged = true
    }

    private fun setFilterChipChecked(filter : String) {
        if (setFilterChipChecked(layout_team, filter)) {
            return
        }

        if (setFilterChipChecked(layout_match_type, filter)) {
            return
        }

        if (setFilterChipChecked(layout_lane, filter)) {
            return
        }

        if (setFilterChipChecked(layout_player, filter)) {
            return
        }

        if (setFilterChipChecked(layout_champion, filter)) {
            return
        }
    }

    private fun setFilterChipChecked(chipGroup: ChipGroup, filter: String) : Boolean {
        for (nIndex in 0 until chipGroup.size) {
            (chipGroup.getChildAt(nIndex) as Chip).let {
                if (it.text.toString().removePrefix("#").compareTo(filter) == 0) {
                    it.isChecked = false
                    return true
                }
            }
        }
        return false
    }
}