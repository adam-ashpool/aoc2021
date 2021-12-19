import Utils.readInput

fun main() {

    // output is a map, where each char ('a'-'g') corresponds to a segment/position (1-7, top to bottom, left to right)
    fun determineConfiguration(hints: List<String>): Map<Char, Int> {
        /***
         * general algorithm logic:
         * - based on string length, each string can only match a few numbers
         * - for given characters, remove segments from the list of possibilities that can not match the current numbers
         * - example: string "ab" -> length is 2, so it must represent number 1 -> chars 'a' and 'b' therefore represent segments 3 and 6 (which one is which has to be determined)
         *   -> for 'a' and 'b', remove all segments other than 3 and 6 from the list of viable possibilities -> repeat for all other hints until only 1 viable option remains for each char
         */
        fun pruneConfiguration(
            hints: Map.Entry<Int, List<String>>,
            viableSegmentConfigurations: MutableMap<Char, MutableList<Int>>
        ): MutableMap<Char, MutableList<Int>> {
            when (hints.key) {
                2 -> hints.value.first()
                    .forEach { viableSegmentConfigurations[it]?.removeAll(setOf(1, 2, 4, 5, 7)) } // 1
                3 -> hints.value.first()
                    .forEach { viableSegmentConfigurations[it]?.removeAll(setOf(2, 4, 5, 7)) }    // 7
                4 -> hints.value.first()
                    .forEach { viableSegmentConfigurations[it]?.removeAll(setOf(1, 5, 7)) }       // 4
                5 -> { // 2, 3, 5
                    val joined = hints.value.joinToString("").groupBy { it }
                    for (c in joined) {
                        when (c.value.size) {
                            // 3 segments common to all of them (1, 4, 7)
                            3 -> viableSegmentConfigurations[c.key]?.removeAll(setOf(2, 3, 5, 6))
                            // could be segments 3 or 6
                            2 -> viableSegmentConfigurations[c.key]?.removeAll(setOf(1, 2, 4, 5, 7))
                            // could be 2 or 5
                            1 -> viableSegmentConfigurations[c.key]?.removeAll(setOf(1, 3, 4, 6, 7))
                        }
                    }
                }
                6 -> { // 6, 9, 0
                    val joined = hints.value.joinToString("").groupBy { it }
                    for (c in joined) {
                        when (c.value.size) {
                            4 -> viableSegmentConfigurations[c.key]?.removeAll(setOf(3, 4, 5))
                            2 -> viableSegmentConfigurations[c.key]?.removeAll(setOf(1, 2, 6, 7))
                        }
                    }
                }
                //7 -> // 8, no information would be gained
            }
            return viableSegmentConfigurations
        }

        // initial map (all connections are possible), which will be pruned
        var viableConfigurations = mutableMapOf<Char, MutableList<Int>>()
        for (c in 'a'..'g') {
            viableConfigurations[c] = (1..7).toMutableList()
        }

        for (hs in hints.groupBy { it.length }) {
            viableConfigurations = pruneConfiguration(hs, viableConfigurations)
        }

        // final cleanup to judge cases, where e.g. 'a' can map to 2 segments, but one of them is already unambiguously determined
        while (viableConfigurations.values.any { it.size != 1 }) {
            for ((k, v) in viableConfigurations) {
                if (v.size == 1) viableConfigurations.forEach {
                    if (it.key != k) viableConfigurations[it.key]?.removeAll(setOf(v.first()))
                }
            }
        }

        return viableConfigurations.mapValues { it.value.first() }
    }

    // converts string like "abc" to number 7
    fun stringToNumber(s: String, config: Map<Char, Int>): Int {
        return when (s.map { config[it] }.sortedBy { it }.joinToString("")) {
            "36" -> 1
            "13457" -> 2
            "13467" -> 3
            "2346" -> 4
            "12467" -> 5
            "124567" -> 6
            "136" -> 7
            "1234567" -> 8
            "123467" -> 9
            "123567" -> 0
            else -> throw IllegalStateException("Could not convert $s to number")
        }
    }

    fun part1(input: List<List<List<String>>>): Int {
        val segmentCountsOfSimpleDigits = setOf(2, 3, 4, 7) // digits 1, 7, 4 and 8
        return input.sumOf { line -> line[1].count { segmentCountsOfSimpleDigits.contains(it.length) } }
    }

    fun part2(input: List<List<List<String>>>): Int {
        val solvedNumbers = input.map { row ->
            row[1].map { stringToNumber(it, determineConfiguration(row[0])) }.joinToString("").toInt()
        }
        return solvedNumbers.sum()
    }

    val inputRaw = readInput("Day08")
    val input = inputRaw.map { line -> line.split(" | ").map { it.split(" ") } }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
