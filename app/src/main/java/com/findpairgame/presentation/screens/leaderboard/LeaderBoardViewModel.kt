package com.findpairgame.presentation.screens.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findpairgame.data.entity.ResultsEntity
import com.findpairgame.domain.usecase.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase
): ViewModel() {

    private lateinit var _resultList: LiveData<List<ResultsEntity>>
    val resultList: LiveData<List<ResultsEntity>>
        get() = _resultList

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _resultList = getUserDataUseCase.getUserResults()
        }
    }

}