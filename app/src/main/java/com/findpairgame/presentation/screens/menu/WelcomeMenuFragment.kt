package com.findpairgame.presentation.screens.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import com.findpairgame.databinding.FragmentWelcomeBinding
import com.findpairgame.presentation.extansions.openScreen

@AndroidEntryPoint
class WelcomeMenuFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
    }

    private fun initClickListener() {
        binding.startGame10Btn.setOnClickListener {
            openScreen(WelcomeMenuFragmentDirections.actionWelcomeFragmentToGameFragment(cards = 10))
        }

        binding.startGame20Btn.setOnClickListener {
            openScreen(WelcomeMenuFragmentDirections.actionWelcomeFragmentToGameFragment(cards = 20))
        }

        binding.leaderboardBtn.setOnClickListener {
            openScreen(WelcomeMenuFragmentDirections.actionWelcomeFragmentToLeaderBoardFragment())
        }

        binding.exitBtn.setOnClickListener {
            requireActivity().finish()
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}