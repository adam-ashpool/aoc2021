import Utils.readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    data class Point(val x: Int, val y: Int)
    class Line(val p1: Point, val p2: Point) {
        fun isHorizontal(): Boolean = p1.y == p2.y
        fun isVertical(): Boolean = p1.x == p2.x
        fun isDiagonal(): Boolean = abs(p1.x - p2.x) == abs(p1.y - p2.y)
    }

    fun findDimensions(lines: List<Line>): Point { // the resulting point is the right-bottom corner
        return lines.fold(Point(0, 0)) { acc, line ->
            Point(
                maxOf(acc.x, line.p1.x, line.p2.x),
                maxOf(acc.y, line.p1.y, line.p2.y)
            )
        }
    }

    // for debugging
    fun printGrid(grid: Array<Array<Int>>): Unit {
        grid.map { it.contentToString() }.map { println(it) }
    }

    fun applyLine(line: Line, grid: Array<Array<Int>>): Array<Array<Int>> {
        if (line.isVertical()) {
            val x = line.p1.x
            for (y in min(line.p1.y, line.p2.y)..max(line.p1.y, line.p2.y)) {
                grid[y][x] += 1
            }
        } else if (line.isHorizontal()) {
            val y = line.p1.y
            for (x in min(line.p1.x, line.p2.x)..max(line.p1.x, line.p2.x)) {
                grid[y][x] += 1
            }
        } else if (line.isDiagonal()) {
            val vector = Point(line.p2.x - line.p1.x, line.p2.y - line.p1.y)
            val length = abs(line.p1.x - line.p2.x) // taking x or y should not matter here
            val step = Point(vector.x / length, vector.y / length)
            for (i in 0..length) {
                grid[line.p1.y + i * step.y][line.p1.x + i * step.x] += 1
            }
        }
        return grid
    }

    fun part1(lines: List<Line>, dimensions: Point): Int {
        val validLines = lines.filter { it.isHorizontal() || it.isVertical() }
        val emptyGrid = Array(dimensions.x + 1) { Array(dimensions.y + 1) { 0 } }
        val finalGrid = validLines.fold(emptyGrid) { acc, line -> applyLine(line, acc) }
        return finalGrid.sumOf { row -> row.count { it > 1 } } // count how many cells is passed by more than 1 line
    }

    fun part2(lines: List<Line>, dimensions: Point): Int {
        val validLines = lines.filter { it.isHorizontal() || it.isVertical() || it.isDiagonal() }
        val emptyGrid = Array(dimensions.x + 1) { Array(dimensions.y + 1) { 0 } }
        val finalGrid = validLines.fold(emptyGrid) { acc, line -> applyLine(line, acc) }
        return finalGrid.sumOf { row -> row.count { it > 1 } } // count how many cells is passed by more than 1 line
    }

    val input = readInput("Day05")
    val linesRaw = input.map { it.split(" -> ").map { p -> p.split(',').map { coord -> coord.toInt() } } }
    val lines = linesRaw.map { Line(Point(it[0][0], it[0][1]), Point(it[1][0], it[1][1])) }
    val dimensions = findDimensions(lines)

    println("Part 1: ${part1(lines, dimensions)}")
    println("Part 2: ${part2(lines, dimensions)}")
}
