package com.mjjang.lolfamousmatch

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.mjjang.lolfamousmatch.adapter.MatchListAdapter
import com.mjjang.lolfamousmatch.data.Match
import com.mjjang.lolfamousmatch.databinding.FragmentMatchListBinding
import com.mjjang.lolfamousmatch.firestore.FireStoreProc
import com.mjjang.lolfamousmatch.utilities.InjectorUtils
import com.mjjang.lolfamousmatch.viewmodels.MatchListViewModel

class MatchListFragment : Fragment() {

    companion object {
        var backKeyPressTime : Long = System.currentTimeMillis();
        val BACK_KEY_PRESS_TIME : Int = 2000;
    }

    private val viewModel: MatchListViewModel by viewModels {
        InjectorUtils.provideMatchListViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMatchListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = MatchListAdapter()
        binding.listMatch.adapter = adapter
        subscribeUi(adapter)

        binding.listMatch.addItemDecoration(RecyclerViewDecoration(resources.getDimension(R.dimen.margin_normal).toInt()))

        (binding.listMatch.adapter as MatchListAdapter).registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                binding.textEmptyList.visibility = (if (adapter.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })

        activity?.onBackPressedDispatcher?.addCallback {
            val systemTiem = System.currentTimeMillis()
            if (systemTiem > backKeyPressTime + BACK_KEY_PRESS_TIME) {
                backKeyPressTime = systemTiem
                Toast.makeText(activity, R.string.back_press_message, Toast.LENGTH_SHORT).show()
            } else {
                activity?.finish()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FireStoreProc.getMatchAll()
    }

    class RecyclerViewDecoration(
        private val height: Int
    ) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) {
                outRect.bottom = height
            }
        }
    }

    private fun subscribeUi(adapter: MatchListAdapter) {
        viewModel.matchs.observe(viewLifecycleOwner) { matchs ->
            adapter.submitList(matchs)
        }
    }
}