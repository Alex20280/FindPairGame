package com.findpairgame.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.findpairgame.R
import com.findpairgame.data.Card
import com.findpairgame.databinding.FragmentGameBinding
import com.findpairgame.databinding.FragmentLeaderBoardBinding
import dagger.hilt.android.AndroidEntryPoint


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
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initialize(args.cards)
        startTimer()
        observeTimer()
        setupRecyclerAdapter()


        //val cards = args.cards
    }



    private fun setupRecyclerAdapter() {
        val cardsCount = args.cards
        binding.cardsRecyclerView.layoutManager = GridLayoutManager(context, if (cardsCount == 10) 4 else 5)
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

    private fun startTimer() {
        viewModel.startTimer()
    }

/*    private fun setupGame(cardCount: Int) {
        val totalCards = cardCount.takeIf { it % 2 == 0 } ?: cardCount + 1 // ensure even
        val images = (1..(totalCards / 2)).map { getImageForId(it) }
        val pairs = (images + images).shuffled()

        cardList = pairs.mapIndexed { index, imageResId ->
            Card(id = index, imageResId = imageResId)
        }.toMutableList()

        adapter = CardAdapter(cardList) { position -> onCardClicked(position) }

        binding.cardsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.cardsRecyclerView.adapter = adapter
    }*/


}