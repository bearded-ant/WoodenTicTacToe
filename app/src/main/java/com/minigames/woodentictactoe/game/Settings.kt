package com.minigames.woodentictactoe.game

data class Settings(
    var whoGoesFirst: Char = X_THE_FIRST,
    var gameMode: Int = PLAYER_VS_BOT,
    var gameDifficulty: Int = EASY
)
