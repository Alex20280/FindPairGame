package com.findpairgame.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.findpairgame.data.dao.ResultsDao
import com.findpairgame.data.entity.ResultsEntity

@Database(
    entities = [ResultsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun resultsDao(): ResultsDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}