package com.findpairgame.data


data class Card(
    val id: Int,
    val imageResId: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false
)