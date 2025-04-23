package com.findpairgame.domain.usecase

import com.findpairgame.data.entity.ResultsEntity
import com.findpairgame.domain.repository.LeaderBoardRepository

class InsertUserDataUseCase (private val repository: LeaderBoardRepository) {

    suspend fun insertUserResults(result: ResultsEntity) {
        repository.insertResults(result)
    }
}