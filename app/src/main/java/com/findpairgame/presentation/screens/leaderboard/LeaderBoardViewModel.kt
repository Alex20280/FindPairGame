package com.findpairgame.presentation.screens.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.findpairgame.data.entity.ResultsEntity
import com.findpairgame.domain.usecase.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase
): ViewModel() {

    private val _resultList = MutableLiveData<ResultsEntity>()
    val resultList: LiveData<ResultsEntity> = _resultList

    init {
        getUserResults()
    }

    private fun getUserResults() {
        TODO("Not yet implemented")
    }

}