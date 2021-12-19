import Utils.readInput
import java.math.BigInteger

fun main() {

    fun updateStateByOneDay(state: MutableMap<Int, BigInteger>): MutableMap<Int, BigInteger> {
        val newState = state.toMutableMap()
        for (i in 0..8) {
            if (i == 8 && state.containsKey(i)) {
                newState[i] = state[0]!!
            } else if (i == 6 && (state.containsKey(7) || state.containsKey(0))) {
                newState[i] = state[0]!! + state[7]!!
            } else {
                newState[i] = state[i + 1]!!
            }
        }
        return newState
    }

    fun updateStateByNDays(state: MutableMap<Int, BigInteger>, days: Int): MutableMap<Int, BigInteger> {
        return if (days < 1) state else {
            updateStateByNDays(updateStateByOneDay(state), days - 1)
        }
    }

    fun part1(input: List<Int>): BigInteger {
        val initialState = mutableMapOf<Int, BigInteger>()
        for (i in 0..8) {
            initialState[i] = input.count { it == i }.toBigInteger()
        }
        return updateStateByNDays(initialState, 80).entries.sumOf { it.value }
    }

    fun part2(input: List<Int>): BigInteger {
        val initialState = mutableMapOf<Int, BigInteger>()
        for (i in 0..8) {
            initialState[i] = input.count { it == i }.toBigInteger()
        }
        return updateStateByNDays(initialState, 256).entries.sumOf { it.value }
    }

    val input = readInput("Day06").first().split(',').map { it.toInt() }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
