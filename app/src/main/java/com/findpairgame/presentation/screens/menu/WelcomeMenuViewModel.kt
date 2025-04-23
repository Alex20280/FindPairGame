package com.findpairgame.presentation.screens.menu

import androidx.lifecycle.ViewModel
import com.findpairgame.domain.usecase.GetThemeUseCase
import com.findpairgame.domain.usecase.SaveThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeMenuViewModel @Inject constructor(
    private val saveThemeUseCase: SaveThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase
) : ViewModel() {

    fun setTheme(isDarkMode: Boolean) {
        saveThemeUseCase.setTheme(isDarkMode)
    }

    fun getTheme(): Boolean {
        return getThemeUseCase.getTheme()
    }

}

