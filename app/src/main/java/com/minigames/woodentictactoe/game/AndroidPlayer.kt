package com.minigames.woodentictactoe.game

data class Node(val score: Int, val move: Move)
data class Move(val row: Int, val column: Int)

private const val HUMAN: Char = 'x'
private const val ANDROID: Char = 'o'

class AndroidPlayer {
    fun easyMove(gameState: GameState): Int {
        val availableHint = gameState.availableMoves.random()
        val space = 3 * availableHint.first + availableHint.second
        if (gameState.isBlankSpace(space)) {
            return space
        }
        return 0
    }

    fun hardMove(board: Board, seed: Char, depth: Int): Int {
        val gameState = GameState(board)
        val availableHint = gameState.availableMoves

//        val availableHint = board.blankSpaces
        var bestScore = if (seed == 'x') Int.MIN_VALUE else Int.MAX_VALUE
        var bestMove: Int? = null

        if (depth == 0 || gameState.isOver()) {
            //todo выход из рекурсии, возврат лучшего хода
            bestMove = 10
        } else {
//            val newBoard = board.getBoard()
            for (available in gameState.availableMoves) {
                val space = 3 * available.first + available.second
                gameState.makeMove(space,seed)
                if (seed == 'x') {
                    val currentScore = hardMove(board, 'o', depth - 1)
                    if (currentScore > bestScore) {
                        bestScore = currentScore
                        bestMove = space
                    }
                }
                else {
                    val currentScore = hardMove(board, 'x', depth - 1)
                    if (currentScore < bestScore) {
                        bestScore = currentScore
                        bestMove = space
                    }
                }
            }
        }

        val score =
            if (gameState.winnerExists() == ANDROID) 10
            else if (gameState.winnerExists() == HUMAN) -10
            else if (availableHint.size == 0) 0
            else 0

        for (i in 0..availableHint.lastIndex) {
            val move = mutableListOf<Int>()

        }
        return 0
    }


}