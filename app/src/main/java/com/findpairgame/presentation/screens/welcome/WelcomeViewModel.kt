package com.findpairgame.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {

    private val _navigateToNext = MutableStateFlow(false)
    val navigateToNext: StateFlow<Boolean> = _navigateToNext

    init {
        viewModelScope.launch {
            delay(3000)
            _navigateToNext.value = true
        }
    }

}

