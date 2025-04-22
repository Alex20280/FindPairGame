package com.findpairgame.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.findpairgame.R
import com.findpairgame.data.Card
import com.findpairgame.databinding.ItemCardBinding

class CardAdapter(
    private var cards: List<Card>,
    private val onCardClicked: (Card) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            if (card.isMatched || card.isFaceUp) {
                binding.cardImage.setImageResource(card.imageResId)
            } else {
                binding.cardImage.setImageResource(R.drawable.card_back)
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

    fun updateCards(newCards: List<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }
}