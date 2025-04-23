package com.findpairgame.domain.repository

interface SettingsRepository {

    fun setTheme(isDarkMode: Boolean)

    fun getTheme(): Boolean
}