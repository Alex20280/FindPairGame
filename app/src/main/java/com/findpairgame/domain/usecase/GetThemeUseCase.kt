package com.findpairgame.domain.usecase

import com.findpairgame.domain.repository.SettingsRepository

class GetThemeUseCase(
    private val settingsRepository: SettingsRepository
) {

    fun getTheme(): Boolean {
        return settingsRepository.getTheme()
    }
}