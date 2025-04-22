package com.findpairgame.domain.usecase

import android.util.Log
import com.findpairgame.data.entity.ResultsEntity
import com.findpairgame.domain.repository.LeaderBoardRepository

class InsertUserDataUseCase (private val repository: LeaderBoardRepository) {

    suspend fun inserUserResults(result: ResultsEntity) {
        Log.d("UseCase", result.time.toString())
        repository.insertResults(result)
    }
}