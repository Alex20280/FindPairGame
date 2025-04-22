package com.findpairgame.domain.repository

import androidx.lifecycle.LiveData
import com.findpairgame.data.entity.ResultsEntity

interface LeaderBoardRepository {

    suspend fun insertResults(result: ResultsEntity)

    suspend fun getUserResults(): LiveData<List<ResultsEntity>>
}