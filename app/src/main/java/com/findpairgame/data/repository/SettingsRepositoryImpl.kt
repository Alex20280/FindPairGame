package com.findpairgame.data.repository

import android.content.SharedPreferences
import com.findpairgame.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): SettingsRepository {

    companion object {
        private const val THEME_KEY = "theme_mode"
    }

    override fun setTheme(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(THEME_KEY, isDarkMode).apply()
    }

    override fun getTheme(): Boolean {
        return sharedPreferences.getBoolean(THEME_KEY, false)
    }
}