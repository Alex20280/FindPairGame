package com.findpairgame.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_results")
data class ResultsEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val time: Long,
    val pattern: Int,
    val date: String,
)