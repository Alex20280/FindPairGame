package com.findpairgame.presentation.screens.game

import android.os.Handler
import android.os.Looper
import android.util.Log
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
): ViewModel() {

    private val _timeText = MutableLiveData<String>()
    val timeText: LiveData<String> = _timeText

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    private val _isGameFinished = MutableLiveData<Boolean>()
    val isGameFinished: LiveData<Boolean> = _isGameFinished

    private var cardCount: Int = 12 // Default value

    private var startTime = 0L
    //private val handler = Handler(Looper.getMainLooper())

    private var selectedCards = mutableListOf<Card>()

    private val timerHandler = Handler(Looper.getMainLooper())



    fun startGame(initialCount: Int) {
        _isGameFinished.value = false
        startTimer()
        // Validate and adjust card count
        cardCount = if (initialCount % 2 == 0) initialCount else initialCount + 1

        _cards.value = shuffleCards(cardCount)
    }

    fun saveDataToDatabase() {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = formatter.format(Date())
        val spentTime = getSpentTime()

        viewModelScope.launch {
            insertUserDataUseCase.inserUserResults(ResultsEntity(0, spentTime, cardCount, date))
        }
    }


    private fun shuffleCards(count: Int): List<Card> {
        val images = listOf(
            R.drawable.apple_icon, R.drawable.bananas_icon, R.drawable.cherries_icon,
            R.drawable.dragon_fruit_icon, R.drawable.grapes_icon, R.drawable.lemon_icon,
            R.drawable.orange_icon, R.drawable.passion_fruit_icon, R.drawable.strawberry_icon,
            R.drawable.watermelon_icon
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
            val millis = elapsed % 1000

            _timeText.value = String.format("Time: %02d:%02d:%03d", minutes, seconds, millis)

            timerHandler.postDelayed(this, 50) // update every 50ms for smooth milliseconds
        }
    }

    fun startTimer() {
        startTime = System.currentTimeMillis()
        timerHandler.post(timeRunnable)
    }

    fun stopTimer() {
        timerHandler.removeCallbacks(timeRunnable)
    }

    private fun getSpentTime(): Long {
        return System.currentTimeMillis() - startTime
    }

    override fun onCleared() {
        stopTimer()
        super.onCleared()
    }

    fun onCardClicked(card: Card) {
        // Prevent unnecessary operations
        if (card.isFaceUp || selectedCards.size == 2) return

        val currentCards = _cards.value?.toMutableList() ?: return
        val clickedIndex = currentCards.indexOfFirst { it.id == card.id }
        if (clickedIndex == -1) return

        // Flip the card and update state
        currentCards[clickedIndex] = currentCards[clickedIndex].copy(isFaceUp = true)
        selectedCards.add(currentCards[clickedIndex])
        _cards.value = currentCards

        // If two cards are selected, check for a match after delay
        if (selectedCards.size == 2) {
            timerHandler.postDelayed(::checkForCardMatch, 500)
        }
    }

    private fun checkForCardMatch() {
        if (selectedCards.size != 2) return

        val currentCards = _cards.value?.toMutableList() ?: return
        val (firstCard, secondCard) = selectedCards

        val firstIndex = currentCards.indexOfFirst { it.id == firstCard.id }
        val secondIndex = currentCards.indexOfFirst { it.id == secondCard.id }

        if (firstCard.imageResId == secondCard.imageResId) {
            // Match found
            if (firstIndex != -1) {
                currentCards[firstIndex] = currentCards[firstIndex].copy(isMatched = true)
            }
            if (secondIndex != -1) {
                currentCards[secondIndex] = currentCards[secondIndex].copy(isMatched = true)
            }

            // Check if all pairs are matched
            if (currentCards.all { it.isMatched }) {
                _isGameFinished.value = true
                //timerHandler.removeCallbacks(timeRunnable) // Stop timer
            }

        } else {
            // No match - flip back
            if (firstIndex != -1) {
                currentCards[firstIndex] = currentCards[firstIndex].copy(isFaceUp = false)
            }
            if (secondIndex != -1) {
                currentCards[secondIndex] = currentCards[secondIndex].copy(isFaceUp = false)
            }
        }

        _cards.value = currentCards
        selectedCards.clear()
    }

/*    private fun checkForCardMatch() {
        if (selectedCards.size != 2) return

        val currentCards = _cards.value?.toMutableList() ?: return
        val (firstCard, secondCard) = selectedCards

        val firstIndex = currentCards.indexOfFirst { it.id == firstCard.id }
        val secondIndex = currentCards.indexOfFirst { it.id == secondCard.id }

        if (firstCard.imageResId == secondCard.imageResId) {
            // Cards match – mark them as matched
            if (firstIndex != -1) {
                currentCards[firstIndex] = currentCards[firstIndex].copy(isMatched = true)
            }
            if (secondIndex != -1) {
                currentCards[secondIndex] = currentCards[secondIndex].copy(isMatched = true)
            }
        } else {
            // Cards do not match – flip them back down
            if (firstIndex != -1) {
                currentCards[firstIndex] = currentCards[firstIndex].copy(isFaceUp = false)
            }
            if (secondIndex != -1) {
                currentCards[secondIndex] = currentCards[secondIndex].copy(isFaceUp = false)
            }
        }

        _cards.value = currentCards
        selectedCards.clear()
    }*/
}