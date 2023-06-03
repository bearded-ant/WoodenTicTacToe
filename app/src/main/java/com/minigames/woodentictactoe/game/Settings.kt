package com.minigames.woodentictactoe.game

const val X_THE_FIRST: Char = 'x'
const val O_THE_FIRST: Char = 'o'
const val PLAYER_VS_PLAYER = 1
const val PLAYER_VS_BOT = 0
const val EASY = 0
const val HARD = 1

data class Settings(
    var whoGoesFirst: Char = X_THE_FIRST,
    var gameMode: Int = PLAYER_VS_BOT,
    var gameDifficulty: Int = EASY
)
