package com.findpairgame.data.repository

import androidx.lifecycle.LiveData
import com.findpairgame.data.dao.ResultsDao
import com.findpairgame.data.entity.ResultsEntity
import com.findpairgame.domain.repository.LeaderBoardRepository

class LeaderBoardRepositoryImpl @javax.inject.Inject constructor(
    private val resultsDao: ResultsDao
) : LeaderBoardRepository {

    override suspend fun insertResults(result: ResultsEntity) {
        resultsDao.insert(result)
    }

    override suspend fun getUserResults(): LiveData<List<ResultsEntity>> {
        return resultsDao.getAllResults()
    }

}