package com.findpairgame.domain.usecase

import androidx.lifecycle.LiveData
import com.findpairgame.data.entity.ResultsEntity
import com.findpairgame.domain.repository.LeaderBoardRepository

class GetUserDataUseCase (private val repository: LeaderBoardRepository) {

    suspend fun getUserResults(): LiveData<List<ResultsEntity>> {
        return repository.getUserResults()
    }
}