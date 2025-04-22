package com.findpairgame.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.findpairgame.data.entity.ResultsEntity

@Dao
interface ResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: ResultsEntity)

    @Query("SELECT * FROM user_results ORDER BY time ASC")
    fun getAllResults(): LiveData<List<ResultsEntity>>
}