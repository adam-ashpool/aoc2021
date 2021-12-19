import Utils.readInput

fun main() {

    fun gridRiskLevel(grid: Array<Array<Int>>): Int {
        val gridWidth = grid.first().size
        val gridHeight = grid.size
        fun pointRiskLevel(x: Int, y: Int): Int {
            val currentHeight = grid[y][x]
            // getOrNull to avoid out of bounds exception
            val left = grid[y].getOrNull(x - 1)
            val right = grid[y].getOrNull(x + 1)
            val top = grid.getOrNull(y - 1)?.get(x)
            val bottom = grid.getOrNull(y + 1)?.get(x)
            val neighbours = listOfNotNull(left, right, top, bottom)
            return if (neighbours.all { it > currentHeight }) 1 + currentHeight else 0
        }

        var riskLevel = 0
        for (y in 0 until gridHeight) {
            for (x in 0 until gridWidth) {
                riskLevel += pointRiskLevel(x, y)
            }
        }
        return riskLevel
    }


    fun part1(input: Array<Array<Int>>): Int {
        return gridRiskLevel(input)
    }

    fun part2(input: List<List<List<String>>>): Int {
        TODO()
    }

    val input =
        readInput("Day09").map { row -> row.split("").filter { it.isNotBlank() }.map { it.toInt() }.toTypedArray() }
            .toTypedArray()
    println("Part 1: ${part1(input)}")
    //println("Part 2: ${part2(input)}")
}
