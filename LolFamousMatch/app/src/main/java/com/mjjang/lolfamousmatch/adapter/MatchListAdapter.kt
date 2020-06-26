package com.mjjang.lolfamousmatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mjjang.lolfamousmatch.MatchListFragmentDirections
import com.mjjang.lolfamousmatch.data.Match
import com.mjjang.lolfamousmatch.databinding.ListItemMatchBinding

class MatchListAdapter() : ListAdapter<Match, RecyclerView.ViewHolder>(MatchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MatchViewHolder(
            ListItemMatchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val match = getItem(position)
        (holder as MatchViewHolder).bind(match)
    }

    class MatchViewHolder(
        private val binding: ListItemMatchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.match?.id?.let { matchId ->
                    navigateToDetail(matchId, view)
                }
            }
        }

        private fun navigateToDetail(matchId: Int, view: View) {
            val direction = MatchListFragmentDirections
                .actionFragmentMatchListToFragmentMatchDetail(matchId)
            view.findNavController().navigate(direction)
        }

        fun bind(item: Match) {
            binding.apply {
                match = item
                executePendingBindings()
            }
        }
    }
}

private class MatchDiffCallback : DiffUtil.ItemCallback<Match>() {

    override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
        return oldItem == newItem
    }
}