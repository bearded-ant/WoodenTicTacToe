package com.minigames.woodentictactoe.game

data class Node(val score: Int, val move: Move)
data class Move(val row: Int, val column: Int)

private const val HUMAN: Char = 'o'
private const val ANDROID: Char = 'x'

class AndroidPlayer(gameState: GameState) {

    fun simpleAndroid() {

    }

}