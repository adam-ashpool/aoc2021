sealed class SubmarineMove {
    class Forward(val x: Int) : SubmarineMove()
    class Down(val y: Int) : SubmarineMove()
    class Up(val y: Int): SubmarineMove()

    companion object {
        private val regex = Regex("""(\w+) (\d+)""")
        fun parse(line: String): SubmarineMove {
            return regex.matchEntire(line)!!
                .destructured
                .let { (direction, distance) ->
                    when (direction) {
                        "forward" -> Forward(distance.toInt())
                        "down" -> Down(distance.toInt())
                        "up" -> Up(distance.toInt())
                        else -> throw IllegalArgumentException("Line was not a valid submarine move: $line")
                    }
                }
        }
    }
}