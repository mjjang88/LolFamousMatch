package com.mjjang.lolfamousmatch.utilities

import com.google.android.material.chip.Chip
import com.mjjang.lolfamousmatch.R

object DynamicStyle {

    fun setChipStyle(view: Chip) {
        view.setChipBackgroundColorResource(R.color.white)
        view.setChipStrokeColorResource(R.color.gray)
        view.setChipStrokeWidthResource(R.dimen.chip_stroke_width)
    }
}