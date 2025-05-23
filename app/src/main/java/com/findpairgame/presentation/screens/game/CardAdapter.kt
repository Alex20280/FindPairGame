package com.findpairgame.presentation.screens.game

import android.annotation.SuppressLint
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.findpairgame.R
import com.findpairgame.data.Card
import com.findpairgame.databinding.ItemCardBinding


class CardAdapter(
    private val cardSize: Size,
    private var cards: List<Card>,
    private val onCardClicked: (Card) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            // Set the size of the card
         val params = binding.root.layoutParams
            params.width = cardSize.width
            params.height = cardSize.height
            binding.root.layoutParams = params

            if (card.isMatched || card.isFaceUp) {
                binding.cardImage.setImageResource(card.imageResId)
            } else {
                binding.cardImage.setImageResource(R.drawable.card_back_icon)
            }

            binding.root.setOnClickListener {
                if (!card.isFaceUp && !card.isMatched) {
                    onCardClicked(card)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCards(newCards: List<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }
}
