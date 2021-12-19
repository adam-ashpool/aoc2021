import Utils.readInput

fun main() {
    data class SubmarinePosition(val distance: Int, val depth: Int, val aim: Int)

    fun part1(input: List<SubmarineMove>): Int {
        val (distance, depth, _) = input.fold(SubmarinePosition(0, 0, 0)) { acc, current ->
            when (current) {
                is SubmarineMove.Forward -> SubmarinePosition(acc.distance + current.x, acc.depth, acc.aim)
                is SubmarineMove.Down -> SubmarinePosition(acc.distance, acc.depth + current.y, acc.aim)
                is SubmarineMove.Up -> SubmarinePosition(acc.distance, acc.depth - current.y, acc.aim)
            }
        }
        return distance * depth
    }

    fun part2(input: List<SubmarineMove>): Int {
        val (distance, depth, _) = input.fold(SubmarinePosition(0, 0, 0)) { acc, current ->
            when (current) {
                is SubmarineMove.Forward -> acc.run {
                    copy(
                        distance = distance + current.x,
                        depth = depth + current.x * aim
                    )
                }
                is SubmarineMove.Down -> acc.run { copy(aim = aim + current.y) }
                is SubmarineMove.Up -> acc.run { copy(aim = aim - current.y) }
            }
        }
        return distance * depth
    }

    val input = readInput("Day02").map { SubmarineMove.parse(it) }
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
