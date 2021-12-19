import Utils.readInput

fun main() {
    fun part1(input: List<Int>): Int {
        return input.foldRightIndexed(0) { index, current, acc ->
            if (index > 0 && current > input[index - 1]) { // need to skip first element
                acc + 1
            } else acc
        }
    }

    fun part2(input: List<Int>): Int {
        val sumsOfSlidingWindows = input.mapIndexed { index, current ->
            if (index < input.size - 2) {
                current + input[index + 1] + input[index + 2]
            } else 0 // last 2 elements will be ignored
        }
        return part1(sumsOfSlidingWindows)
    }

    val input = readInput("Day01").map(String::toInt)
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
