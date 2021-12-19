import Utils.readInput
import kotlin.math.abs

fun main() {

    fun costOfAligningAtPosition(state: List<Int>, position: Int): Int {
        return state.sumOf { abs(it - position) }
    }

    fun costOfAligningAtPositionV2(state: List<Int>, position: Int): Int {
        fun fuelCostPerDistance(d: Int) = (d * d + d) / 2 // from binomial distribution, i.e. d + (d-1) + (d-2)...
        return state.sumOf { fuelCostPerDistance(abs(it - position)) }
    }

    fun part1(input: List<Int>): Int {
        require(input.isNotEmpty())
        val costs = mutableMapOf<Int, Int>()
        for (i in input.minOrNull()?.rangeTo(input.maxOrNull()!!)!!) {
            costs[i] = costOfAligningAtPosition(input, i)
        }
        return costs.minOf { it.value }
    }

    fun part2(input: List<Int>): Int {
        require(input.isNotEmpty())
        val costs = mutableMapOf<Int, Int>()
        for (i in input.minOrNull()?.rangeTo(input.maxOrNull()!!)!!) {
            costs[i] = costOfAligningAtPositionV2(input, i)
        }
        return costs.minOf { it.value }
    }

    val input = readInput("Day07").first().split(',').map { it.toInt() }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
