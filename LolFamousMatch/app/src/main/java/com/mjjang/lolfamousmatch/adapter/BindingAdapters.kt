package com.mjjang.lolfamousmatch.adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.mjjang.lolfamousmatch.R
import com.mjjang.lolfamousmatch.utilities.DynamicStyle
import kr.co.prnd.YouTubePlayerView

@BindingAdapter("youtubeVideoId")
fun bindYoutubeVideoId(view: YouTubePlayerView, videoId: String?) {
    if (!videoId.isNullOrEmpty()) {
        view.play(videoId)
    }
}

@BindingAdapter("youtubeThumbnailFromId")
fun bindYoutubeThumbnailFromId(view: ImageView, videoId: String?) {
    if (!videoId.isNullOrEmpty()) {
        val thumbNailUrl = "https://img.youtube.com/vi/$videoId/mqdefault.jpg"
        Glide.with(view.context)
            .load(thumbNailUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("addTag")
fun bindAddTag(view: ViewGroup, tags: String?) {
    view.removeAllViews()
    if (!tags.isNullOrEmpty()) {
        val tagArray = tags.split(",")
        for (tag in tagArray) {
            val chip = Chip(view.context)
            chip.text = "#$tag"
            DynamicStyle.setChipStyle(chip)
            view.addView(chip)
        }
    } else {
        view.visibility = View.GONE
    }
}