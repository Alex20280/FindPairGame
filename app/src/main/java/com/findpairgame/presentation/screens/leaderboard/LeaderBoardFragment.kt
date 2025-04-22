package com.findpairgame.presentation.screens.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.findpairgame.databinding.FragmentLeaderBoardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderBoardFragment : Fragment() {

    private var _binding: FragmentLeaderBoardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLeaderBoardBinding.inflate(inflater, container, false)
        return binding.root
    }
}