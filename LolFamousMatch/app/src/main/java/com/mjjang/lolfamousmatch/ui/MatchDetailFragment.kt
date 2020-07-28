package com.mjjang.lolfamousmatch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.google.android.material.chip.Chip
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.mjjang.lolfamousmatch.databinding.FragmentMatchDetailBinding
import com.mjjang.lolfamousmatch.utilities.DynamicStyle
import com.mjjang.lolfamousmatch.utilities.InjectorUtils
import com.mjjang.lolfamousmatch.viewmodels.MatchDetailViewModel
import kotlinx.android.synthetic.main.fragment_match_detail.*
import kr.co.prnd.YouTubePlayerView.OnInitializedListener

class MatchDetailFragment : Fragment(), OnInitializedListener {

    private val args: MatchDetailFragmentArgs by navArgs()

    private val matchDetailViewModel: MatchDetailViewModel by viewModels {
        InjectorUtils.provideMatchDetailViewModelFactory(this, args.matchId)
    }

    private var mPlayer : YouTubePlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMatchDetailBinding.inflate(inflater, container, false).apply {
            viewModel = matchDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        context ?: return binding.root

        activity?.onBackPressedDispatcher?.addCallback {
            view?.findNavController()?.navigateUp()
        }

        matchDetailViewModel.match.observe(viewLifecycleOwner, Observer {
            // set Youtube Video
            it.link_highlight?.let { linkHighlight ->
                youtube_player_view.play(linkHighlight, this)
            }


            // set chip
            val tagArray = it.tag?.split(",")
            if (tagArray != null) {
                for (tag in tagArray) {
                    val chip = Chip(activity)
                    chip.text = "#$tag"
                    DynamicStyle.setChipStyle(chip)
                    binding.layoutChipGroup.addView(chip)
                }
            }
        })

        binding.adView.loadAd(AdRequest.Builder().build())

        return binding.root
    }

    override fun onPause() {
        mPlayer?.pause()
        super.onPause()
    }

    private fun navigateToSignIn(view: View) {
        val direction =
            MatchDetailFragmentDirections.actionFragmentMatchDetailToFragmentSignIn()
        view.findNavController().navigate(direction)
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider,
        result: YouTubeInitializationResult
    ) {
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        wasRestored: Boolean
    ) {
        mPlayer = player
    }
}