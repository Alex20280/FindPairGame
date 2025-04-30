package com.findpairgame.presentation.screens.game

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Size
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.findpairgame.R
import com.findpairgame.databinding.FragmentGameBinding
import com.findpairgame.presentation.extansions.goBack
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
        setText()
        onPauseClickListener()

    }

    private fun onPauseClickListener() {
        binding.pauseIv.setOnClickListener {
            viewModel.toggleTimer()
        }
    }

    private fun setText() {
        binding.titleTv.text = getString(R.string.mode_x10, args.cards)
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
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.custom_game_over_dialog, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val yesButton = dialogView.findViewById<ImageView>(R.id.yesButton)
        val noButton = dialogView.findViewById<ImageView>(R.id.noButton)

        yesButton.setOnClickListener {
            dialog.dismiss()
            viewModel.saveDataToDatabase()
            viewModel.startGame(args.cards)
        }

        noButton.setOnClickListener {
            dialog.dismiss()
            viewModel.saveDataToDatabase()
            goBack()
        }

        dialog.show()

        val widthInDp = 300
        val widthInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            widthInDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        dialog.window?.setLayout(widthInPx, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setupRecyclerAdapter() {
        val displayMetrics = resources.displayMetrics
        val dpToPx = { dp: Int -> (dp * displayMetrics.density).toInt() }

        val cardWidth = when (args.cards) {
            10 -> dpToPx(64)
            20 -> dpToPx(60)
            else -> dpToPx(56)
        }

        val cardHeight = when (args.cards) {
            10 -> dpToPx(108)
            20 -> dpToPx(115)
            else -> dpToPx(95)
        }

        val cardSize = Size(cardWidth, cardHeight)

        adapter = CardAdapter(cardSize, emptyList()) { card ->
            viewModel.onCardClicked(card)
        }

        val layoutManager = CustomCardLayoutManager(requireContext(), cardSize)

        binding.cardsRecyclerView.apply {
            this.layoutManager = layoutManager
            adapter = this@GameFragment.adapter

            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val space = dpToPx(4)
                    outRect.set(space, space, space, space)
                }
            })
        }

        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            adapter.updateCards(cards)
            binding.cardsRecyclerView.requestLayout()
        }
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