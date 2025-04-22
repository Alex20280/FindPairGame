package com.findpairgame.presentation.screens.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.findpairgame.databinding.FragmentLeaderBoardBinding
import com.findpairgame.presentation.extansions.goBack
import com.findpairgame.presentation.screens.game.CardAdapter
import com.findpairgame.presentation.screens.game.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LeaderBoardFragment : Fragment() {

    private var _binding: FragmentLeaderBoardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LeaderBoardViewModel by viewModels()
    private lateinit var adapter: LeaderBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaderBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerAdapter()
        observeUserResults()

    }

    private fun setupRecyclerAdapter() {
        binding.resultsRv.layoutManager = GridLayoutManager(context, 2)
        adapter = LeaderBoardAdapter(emptyList())
        binding.resultsRv.adapter = adapter
    }

    private fun observeUserResults() {
        viewModel.resultList.observe(viewLifecycleOwner) { resultList ->
            adapter.updateResults(resultList)
        }
    }
}