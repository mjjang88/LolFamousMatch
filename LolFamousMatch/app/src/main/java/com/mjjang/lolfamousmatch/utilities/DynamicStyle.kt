package com.mjjang.lolfamousmatch.utilities

import com.google.android.material.chip.Chip
import com.mjjang.lolfamousmatch.R

object DynamicStyle {

    fun setChipStyle(view: Chip) {
        view.setChipBackgroundColorResource(R.color.white)
        view.setChipStrokeColorResource(R.color.gray)
        view.setChipStrokeWidthResource(R.dimen.chip_stroke_width)
        view.setTextColor(view.resources.getColor(R.color.gray, null))
        view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, view.resources.getDimensionPixelSize(R.dimen.text_size_normal).toFloat())
    }

    fun setChipFilterStyle(view: Chip) {
        view.isCheckable = true
        view.isChipIconVisible = false
        view.isCloseIconVisible = false
        view.checkedIcon = null
        view.chipBackgroundColor = view.resources.getColorStateList(R.color.filter_chip_backgound_color_selector, null)
        view.setTextColor(view.resources.getColorStateList(R.color.filter_chip_text_color_selector, null))
    }
}