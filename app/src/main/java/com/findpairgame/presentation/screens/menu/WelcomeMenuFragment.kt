package com.findpairgame.presentation.screens.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.findpairgame.databinding.FragmentWelcomeBinding
import com.findpairgame.presentation.extansions.openScreen

@AndroidEntryPoint
class WelcomeMenuFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WelcomeMenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleThemeChange()
        initClickListener()

    }

    private fun handleThemeChange() {
        val isDarkTheme = viewModel.getTheme()

        binding.themeSwitch.isChecked = isDarkTheme

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            viewModel.setTheme(isChecked)
            binding.root.postDelayed({
                activity?.recreate()
            }, 300)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.setTheme(binding.themeSwitch.isChecked)
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