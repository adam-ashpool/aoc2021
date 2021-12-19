import Utils.findLeastCommonChar
import Utils.findLeastCommonCharOrDefault
import Utils.findMostCommonChar
import Utils.findMostCommonCharOrDefault
import Utils.readInput
import Utils.transpose

fun main() {
    fun part1(input: List<String>): Int {
        val gammaBinaryString = input.transpose().map { it.findMostCommonChar() }.joinToString("")
        val gamma = Integer.parseInt(gammaBinaryString, 2)
        val epsilonBinaryString = input.transpose().map { it.findLeastCommonChar() }.joinToString("")
        val epsilon = Integer.parseInt(epsilonBinaryString, 2)
        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val cols = input.first().length

        fun findOxygenRatingBinary(col: Int, possibleAnswers: List<String>): String {
            val mostCommonChar = possibleAnswers.transpose()[col].findMostCommonCharOrDefault('1')
            val filtered = possibleAnswers.filter { it[col] == mostCommonChar }
            return if (filtered.size == 1) filtered.first()
            else findOxygenRatingBinary(col + 1, filtered)
        }

        fun findCO2ScrubberRatingBinary(col: Int, possibleAnswers: List<String>): String {
            val leastCommonChar = possibleAnswers.transpose()[col].findLeastCommonCharOrDefault('0')
            val filtered = possibleAnswers.filter { it[col] == leastCommonChar }
            return if (filtered.size == 1) filtered.first()
            else findCO2ScrubberRatingBinary(col + 1, filtered)
        }

        val oxygenRating = Integer.parseInt(findOxygenRatingBinary(0, input), 2)
        val co2ScrubberRating = Integer.parseInt(findCO2ScrubberRatingBinary(0, input), 2)
        return oxygenRating * co2ScrubberRating
    }

    val input = readInput("Day03")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
