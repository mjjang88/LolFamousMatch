package com.mjjang.lolfamousmatch.adapter

import androidx.databinding.BindingAdapter
import kr.co.prnd.YouTubePlayerView

@BindingAdapter("youtubeVideoId")
fun bindYoutubeVideoId(view: YouTubePlayerView, videoId: String?) {
    if (!videoId.isNullOrEmpty()) {
        view.play(videoId)
    }
}