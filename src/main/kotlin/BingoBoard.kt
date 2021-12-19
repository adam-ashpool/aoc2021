import Utils.transpose

class BingoBoard(private val board: Array<Array<Pair<Int, Boolean>>>) {

    fun isCompleted(): Boolean {
        return board.any { row -> row.all { it.second } } || transpose(board).any { row -> row.all { it.second } }
    }

    fun drawNumber(n: Int) {
        for ((ri, row) in board.withIndex()) {
            for ((ci, field) in row.withIndex()) {
                if (!field.second && field.first == n) board[ri][ci] = Pair(n, true)
            }
        }
    }

    fun sumOfUnmarkedFields(): Int {
        return board.flatten().sumOf { pair -> if (!pair.second) pair.first else 0 }
    }

}