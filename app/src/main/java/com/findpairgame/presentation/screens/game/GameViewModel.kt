package com.findpairgame.presentation.screens.game

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findpairgame.R
import com.findpairgame.data.Card
import com.findpairgame.data.entity.ResultsEntity
import com.findpairgame.domain.usecase.InsertUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val insertUserDataUseCase: InsertUserDataUseCase
) : ViewModel() {

    private val _timeText = MutableLiveData<String>()
    val timeText: LiveData<String> = _timeText

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    private val _isGameFinished = MutableLiveData<Boolean>()
    val isGameFinished: LiveData<Boolean> = _isGameFinished

    private var cardCount: Int = 12
    private var startTime = 0L
    private var elapsedTime = 0L
    private var selectedCards = mutableListOf<Card>()
    private val timerHandler = Handler(Looper.getMainLooper())
    private var isTimerRunning = false

    fun startGame(initialCount: Int) {
        _isGameFinished.value = false
        startOrResumeTimer()
        cardCount = if (initialCount % 2 == 0) initialCount else initialCount + 1

        _cards.value = shuffleCards(cardCount)
    }

    fun saveDataToDatabase() {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = formatter.format(Date())
        val spentTime = getSpentTime()

        viewModelScope.launch {
            insertUserDataUseCase.insertUserResults(ResultsEntity(0, spentTime, cardCount, date))
        }
    }

    private fun shuffleCards(count: Int): List<Card> {
        val images = listOf(
            R.drawable.avocado_icon, R.drawable.blueberry_icon, R.drawable.grape_icon,
            R.drawable.kiwi_icon, R.drawable.mango_icon, R.drawable.pear_icon,
            R.drawable.orange_icon, R.drawable.pineapple_icon, R.drawable.strawberry_icon,
            R.drawable.watermelon_icon, R.drawable.cherry_icon, R.drawable.orangejuice_icon,
            R.drawable.pomegranate_icon, R.drawable.pumpkin_icon, R.drawable.tomato_icon,
            R.drawable.almond_icon
        )
        val pairsNeeded = (if (count % 2 == 0) count else count + 1) / 2
        val selectedImages = images.shuffled().take(pairsNeeded)

        return (selectedImages + selectedImages)
            .shuffled()
            .mapIndexed { index, imageResId ->
                Card(
                    id = index,
                    imageResId = imageResId,
                    isFaceUp = false,
                    isMatched = false
                )
            }
    }

    private val timeRunnable = object : Runnable {
        override fun run() {
            val elapsed = System.currentTimeMillis() - startTime
            val minutes = (elapsed / 1000) / 60
            val seconds = (elapsed / 1000) % 60

            _timeText.value = String.format("%02d:%02d", minutes, seconds)
            timerHandler.postDelayed(this, 1000)
        }
    }

    private fun startOrResumeTimer() {
        if (!isTimerRunning) {
            startTime = System.currentTimeMillis() - elapsedTime
            timerHandler.post(timeRunnable)
            isTimerRunning = true
        }
    }

    private fun pauseTimer() {
        if (isTimerRunning) {
            timerHandler.removeCallbacks(timeRunnable)
            elapsedTime = System.currentTimeMillis() - startTime
            isTimerRunning = false
        }
    }

    fun stopTimer() {
        timerHandler.removeCallbacks(timeRunnable)
        elapsedTime = System.currentTimeMillis() - startTime
    }

    fun toggleTimer() {
        if (isTimerRunning) {
            pauseTimer()
        } else {
            startOrResumeTimer()
        }
    }

    private fun getSpentTime(): Long {
        return if (elapsedTime > 0) elapsedTime else System.currentTimeMillis() - startTime
    }

    override fun onCleared() {
        stopTimer()
        super.onCleared()
    }

    fun onCardClicked(card: Card) {
        if (card.isFaceUp || selectedCards.size == 2) return

        val currentCards = _cards.value?.toMutableList() ?: return
        val clickedIndex = currentCards.indexOfFirst { it.id == card.id }
        if (clickedIndex == -1) return

        currentCards[clickedIndex] = currentCards[clickedIndex].copy(isFaceUp = true)
        selectedCards.add(currentCards[clickedIndex])
        _cards.value = currentCards

        if (selectedCards.size == 2) {
            timerHandler.postDelayed(::checkForCardMatch, 500)
        }
    }

    private fun checkForCardMatch() {
        if (selectedCards.size != 2) return

        val currentCards = _cards.value?.toMutableList() ?: return
        val (firstCard, secondCard) = selectedCards
        val isMatch = firstCard.imageResId == secondCard.imageResId

        listOf(firstCard, secondCard).forEach { card ->
            val index = currentCards.indexOfFirst { it.id == card.id }
            if (index != -1) {
                currentCards[index] = currentCards[index].copy(
                    isMatched = if (isMatch) true else currentCards[index].isMatched,
                    isFaceUp = isMatch
                )
            }
        }

        if (isMatch && currentCards.all { it.isMatched }) {
            _isGameFinished.value = true
        }

        if (!isMatch) {
            listOf(firstCard, secondCard).forEach { card ->
                val index = currentCards.indexOfFirst { it.id == card.id }
                if (index != -1) {
                    currentCards[index] = currentCards[index].copy(isFaceUp = false)
                }
            }
        }

        _cards.value = currentCards
        selectedCards.clear()
    }

}