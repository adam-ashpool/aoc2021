import Utils.readInput

fun main() {
    fun part1(drawnNumbers: List<Int>, boards: List<BingoBoard>): Int? {

        fun drawNumberAndCheckBoards(numbers: List<Int>, boards: List<BingoBoard>): Int? {
            val number = numbers.first()
            for (b in boards) {
                b.drawNumber(number)
            }

            boards.firstOrNull { it.isCompleted() }.let { maybeBoard ->
                return if (maybeBoard != null) {
                    maybeBoard.sumOfUnmarkedFields() * number // final result
                } else {
                    drawNumberAndCheckBoards(numbers.drop(1), boards)
                }
            }
        }

        return drawNumberAndCheckBoards(drawnNumbers, boards)
    }

    fun part2(drawnNumbers: List<Int>, boards: List<BingoBoard>): Int? {
        fun drawNumberAndCheckBoards(numbers: List<Int>, boards: List<BingoBoard>): Int? {
            val number = numbers.first()
            for (b in boards) {
                b.drawNumber(number)
            }

            boards.filter { !it.isCompleted() }.let { remainingBoards ->
                return if (remainingBoards.isEmpty()) boards.first().sumOfUnmarkedFields() * number // last board (assumption: last two boards will not be solved at once)
                else drawNumberAndCheckBoards(numbers.drop(1), remainingBoards)
            }
        }

        return drawNumberAndCheckBoards(drawnNumbers, boards)
    }

    val input = readInput("Day04")
    val drawnNumbers = input.first().split(',').map { it.toInt() }
    val boardsRaw = input.drop(1).filter { it != "" }.chunked(5)
    val boards = mutableListOf<BingoBoard>()
    for (b in boardsRaw) {
        val numbers = b.map { row -> row.trim().split("\\s+".toRegex()).map { it.toInt() } }
        val bingoBoard = Array(5) { Array(5) { Pair(0, false) } } // init empty board
        for ((rowIndex, row) in numbers.withIndex()) {
            for ((colIndex, number) in row.withIndex()) {
                bingoBoard[rowIndex][colIndex] = Pair(number, false)
            }
        }
        boards.add(BingoBoard(bingoBoard))
    }

    println("Part 1: ${part1(drawnNumbers, boards)}")
    println("Part 2: ${part2(drawnNumbers, boards)}")
}
