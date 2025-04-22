package com.findpairgame.presentation.extansions

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.openScreen(navDirections: NavDirections) {
    findNavController().navigate(navDirections)
}

fun Fragment.goBack() {
    if (findNavController().previousBackStackEntry != null) {
        findNavController().navigateUp()
    }
}
