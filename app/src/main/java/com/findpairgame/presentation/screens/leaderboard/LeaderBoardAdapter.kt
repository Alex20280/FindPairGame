package com.findpairgame.presentation.screens.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.findpairgame.R
import com.findpairgame.data.Card
import com.findpairgame.data.entity.ResultsEntity
import com.findpairgame.presentation.screens.game.CardAdapter

class LeaderBoardAdapter(
    private var results: List<ResultsEntity>
) : RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder>() {

    inner class LeaderBoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val patternTv: TextView = itemView.findViewById(R.id.patterTv)
        private val timeTv: TextView = itemView.findViewById(R.id.timeTv)
        private val dateTv: TextView = itemView.findViewById(R.id.dateTv)

        fun bind(result: ResultsEntity) {
            patternTv.text = result.pattern.toString()
            timeTv.text = formatTime(result.time)
            dateTv.text = result.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard, parent, false)
        return LeaderBoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaderBoardViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int = results.size

    fun updateResults(newResults: List<ResultsEntity>) {
        results = newResults
        notifyDataSetChanged()
    }

    private fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}