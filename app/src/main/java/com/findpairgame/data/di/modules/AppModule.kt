package com.findpairgame.data.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.findpairgame.data.dao.ResultsDao
import com.findpairgame.data.database.AppDatabase
import com.findpairgame.data.repository.LeaderBoardRepositoryImpl
import com.findpairgame.domain.repository.LeaderBoardRepository
import com.findpairgame.domain.usecase.GetUserDataUseCase
import com.findpairgame.domain.usecase.InsertUserDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideResultsDao(
        appDatabase: AppDatabase,
    ): ResultsDao = appDatabase.resultsDao()


    @Provides
    @Singleton
    fun provideLeaderboardRepository(resultsDao: ResultsDao): LeaderBoardRepository {
        return LeaderBoardRepositoryImpl(resultsDao)
    }

    @Provides
    fun provideGetUserDataUseCase(
        repository: LeaderBoardRepository
    ): GetUserDataUseCase = GetUserDataUseCase(repository)

    @Provides
    fun provideInsertUserDataUseCase(
        repository: LeaderBoardRepository
    ): InsertUserDataUseCase = InsertUserDataUseCase(repository)

}