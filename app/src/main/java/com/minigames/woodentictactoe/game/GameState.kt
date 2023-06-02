package com.minigames.woodentictactoe.game

private const val DIAGONAL_ID = "d"
private const val REVERSE_DIAGONAL_ID = "rd"
private const val COLUMN_PREFIX = "c"
private const val ROW_PREFIX = "r"
private const val WIDTH = 3

class GameState(
    val playerPiece: Char = 'x',
    val opponentPiece: Char = 'o'
) {

    private val maxIndex: Int = WIDTH - 1
    private val minimumMovesRequiredToWin: Int = (2 * WIDTH) - 1

//    private var impossibleLines: MutableList<String> = mutableListOf()

    private var board = Board(WIDTH)
    var winner: Char? = null

    fun makeMove(space: Int, piece: Char) {
        board = board.placePiece(piece, space)
        checkForWin()
    }

    val availableMoves: MutableList<Pair<Int, Int>> = board.blankSpaces
    val notAvailable = board.toArray()
    val cornerSpaces: List<Pair<Int, Int>>
        get() = listOf(
            Pair(0, 0),
            Pair(0, maxIndex),
            Pair(maxIndex, 0),
            Pair(maxIndex, maxIndex)
        )

    val isUnplayed: Boolean = board.isAllBlank()

//    val finalMove: Int? = if (board.numberOfBlanks == 1) board.blankSpaces.first() else null

    fun isOver(): Boolean = winner != null || isDraw()

    fun isDraw(): Boolean = board.numberOfBlanks == 0 && winner == null

    fun winnerExists(): Boolean {
        return winner != null
    }

//    fun lost(piece: Char): Boolean {
//        return winner != null && winner != piece
//    }

//    fun won(piece: Char): Boolean {
//        return piece == winner
//    }

//    fun winningLine(): List<Pair<Int, Int>> {
//        val gatherLine = true

//        checkForWin()

//        return board.getWinningLine(gatherLine)
//    }

    fun isBlankSpace(space: Int): Boolean = board.isBlank(space)
    private fun checkForWin() {
        if (board.numberOfOccupied >= minimumMovesRequiredToWin) {
            winner =
                winningRow(board.getBoard()) ?: winningColumn(board.getBoard()) ?: winningDiagonal(
                    board.getBoard()
                )
        }
    }

    fun winningRow(board: Array<Array<Char?>>): Char? {
        // Проверка горизонтальных строк
        for (row in 0 until WIDTH) {
            if (board[row][0] != null && board[row][0] == board[row][1] && board[row][0] == board[row][2]) {
                return board[row][0]
            }
        }
        return null
    }

    fun winningColumn(board: Array<Array<Char?>>): Char? {
        // Проверка вертикальных строк
        for (col in 0 until board[0].size) {
            if (board[0][col] != null && board[0][col] == board[1][col] && board[0][col] == board[2][col]) {
                return board[0][col]
            }
        }
        return null
    }

    fun winningDiagonal(board: Array<Array<Char?>>): Char? {
        // Проверка диагоналей
        if (board[0][0] != null && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return board[0][0]
        }
        if (board[0][2] != null && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return board[0][2]
        }
        return null
    }

}
