package com.minigames.woodentictactoe.game

private val BLANK: Char? = null

class Board(private val width: Int) {

    private var numberOfSpaces: Int = 0
    var numberOfBlanks: Int = 0
    var numberOfOccupied: Int = 0
    val blankSpaces: MutableList<Pair<Int, Int>>
    private val board: Array<Array<Char?>>

    init {
        numberOfSpaces = width * width
        numberOfBlanks = numberOfSpaces
        numberOfOccupied = 0
        blankSpaces = squareArray((0 until width).toList()).toMutableList()
        board = Array(width) { Array(width) { BLANK } }
    }

    fun isAllBlank(): Boolean {
        return numberOfBlanks == numberOfSpaces
    }

    fun isBlank(space: Int): Boolean {
        val pair = spaceToPair(space)
        return board[pair.first][pair.second] == BLANK
    }

    fun placePiece(piece: Char, space: Int): Board {
        if (piece != BLANK) {
            val pair = spaceToPair(space)
            board[pair.first][pair.second] = piece
            numberOfBlanks--
            numberOfOccupied++
            blankSpaces.remove(pair)
        }
        return this
    }

    fun contentsOf(space: Pair<Int, Int>): Char? {
        return board[space.first][space.second]
    }

    fun toArray(): List<Char?> {
        return board.flatten()
    }

    fun getBoard(): Array<Array<Char?>> = board


    private fun squareArray(array: List<Int>): List<Pair<Int, Int>> {
        return array.flatMap { i -> array.map { j -> Pair(i, j) } }
    }

    private fun spaceToPair(space: Int): Pair<Int, Int> {
        val row = space / width
        val col = space % width
        return Pair(row, col)
    }
}