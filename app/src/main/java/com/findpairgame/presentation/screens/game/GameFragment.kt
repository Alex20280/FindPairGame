package com.findpairgame.presentation.screens.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.findpairgame.databinding.FragmentGameBinding
import com.findpairgame.presentation.extansions.goBack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val args: GameFragmentArgs by navArgs()
    private val viewModel: GameViewModel by viewModels()
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startGame()
        observeTimer()
        observeGameStatus()
        setupRecyclerAdapter()
    }

    private fun startGame() {
        viewModel.startGame(args.cards)
    }

    private fun observeGameStatus() {
        viewModel.isGameFinished.observe(viewLifecycleOwner) { finished ->
            if (finished) {
                viewModel.stopTimer()

                showGameFinishedDialog()
            }
        }
    }

    private fun showGameFinishedDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Game Over")
            .setMessage("Do you want to start over the game?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                viewModel.saveDataToDatabase()
                viewModel.startGame(args.cards)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
                viewModel.saveDataToDatabase()
                goBack()
            }
            .create()

        alertDialog.show()
    }

    private fun setupRecyclerAdapter() {
        binding.cardsRecyclerView.layoutManager = GridLayoutManager(context, 4)
        adapter = CardAdapter(emptyList()) { card -> viewModel.onCardClicked(card)
        }
        binding.cardsRecyclerView.adapter = adapter

        viewModel.cards.observe(viewLifecycleOwner) { adapter.updateCards(it) }
    }

    private fun observeTimer() {
        viewModel.timeText.observe(viewLifecycleOwner) { time ->
            binding.timerTv.text = time
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}