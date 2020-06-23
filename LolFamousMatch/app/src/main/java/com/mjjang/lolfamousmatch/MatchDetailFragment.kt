package com.mjjang.lolfamousmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.mjjang.lolfamousmatch.databinding.FragmentMatchDetailBinding
import com.mjjang.lolfamousmatch.utilities.InjectorUtils
import com.mjjang.lolfamousmatch.viewmodels.MatchDetailViewModel

class MatchDetailFragment : Fragment() {

    private val args: MatchDetailFragmentArgs by navArgs()

    private val matchDetailViewModel: MatchDetailViewModel by viewModels {
        InjectorUtils.provideMatchDetailViewModelFactory(this, args.matchId)
    }

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

        return binding.root
    }
}