package com.findpairgame.presentation.screens.leaderboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.findpairgame.R
import com.findpairgame.data.entity.ResultsEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class LeaderBoardAdapter(
    private var results: List<ResultsEntity>
) : RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder>() {

    inner class LeaderBoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val patternTv: TextView = itemView.findViewById(R.id.patterTv)
        private val timeTv: TextView = itemView.findViewById(R.id.timeTv)
        private val dateTv: TextView = itemView.findViewById(R.id.dateTv)
        private val rootContainer: View = itemView.findViewById(R.id.rootContainer)

        fun bind(result: ResultsEntity, position: Int) {
            val bgColor = when {
                position % 2 == 0 -> itemView.context.getColor(R.color.orange)
                else -> itemView.context.getColor(R.color.grey)
            }
            rootContainer.setBackgroundColor(bgColor)

            patternTv.text = result.pattern.toString()
            timeTv.text = formatTime(result.time)
            dateTv.text = result.date.dropLast(5)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard, parent, false)
        return LeaderBoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaderBoardViewHolder, position: Int) {
        holder.bind(results[position], position)
    }

    override fun getItemCount(): Int = results.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateResults(newResults: List<ResultsEntity>) {
        results = newResults
        notifyDataSetChanged()
    }

    private fun formatTime(milliseconds: Long): String {
        val totalSeconds = milliseconds / 1000
        val minutes = totalSeconds / 60
        val remainingSeconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}


/*
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
            dateTv.text = result.date.dropLast(5)
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateResults(newResults: List<ResultsEntity>) {
        results = newResults
        notifyDataSetChanged()
    }

    private fun formatTime(milliseconds: Long): String {
        val totalSeconds = milliseconds / 1000
        val minutes = totalSeconds / 60
        val remainingSeconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

}*/
