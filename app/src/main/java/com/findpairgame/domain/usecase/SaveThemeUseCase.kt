package com.findpairgame.domain.usecase

import com.findpairgame.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveThemeUseCase(
    private val settingsRepository: SettingsRepository
) {

    fun setTheme(isDarkMode: Boolean) {
        settingsRepository.setTheme(isDarkMode)
    }

}

/*
class SaveThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : SettingsRepository {

    override fun setTheme(isDarkMode: Boolean) {
        settingsRepository.setTheme(isDarkMode)
    }

    override fun getTheme(): Boolean {
        return settingsRepository.getTheme()
    }
}*/
