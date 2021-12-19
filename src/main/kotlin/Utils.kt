import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

object Utils {
    /**
     * Reads lines from the given input txt file.
     */
    fun readInput(name: String) = File("src/main/resources", "$name.txt").readLines()

    /**
     * Converts string to md5 hash.
     */
    fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

    /**
     * Transpose list of strings.
     */
    fun List<String>.transpose(): List<String> {
        val rows = this.size
        val cols = this[0].length
        val transposed = Array(cols) { Array(rows) { ' ' } } // empty space as default char?
        for (row in (0 until rows)) {
            for (col in (0 until cols)) {
                transposed[col][row] = this[row][col]
            }
        }
        return transposed.map { it.joinToString("") }
    }

    inline fun <reified T> transpose(xs: Array<Array<T>>): Array<Array<T>> {
        val cols = xs[0].size
        val rows = xs.size
        return Array(cols) { j ->
            Array(rows) { i ->
                xs[i][j]
            }
        }
    }

    fun String.findMostCommonChar(): Char {
        return this.groupBy { it }.maxByOrNull { it.value.size }!!.key
    }

    fun String.findLeastCommonChar(): Char {
        return this.groupBy { it }.minByOrNull { it.value.size }!!.key
    }

    fun String.findMostCommonCharOrDefault(def: Char): Char {
        val sorted = this.groupBy { it }
        return if (sorted['0']?.size == sorted['1']?.size) def
        else sorted.maxByOrNull { it.value.size }!!.key
    }

    fun String.findLeastCommonCharOrDefault(def: Char): Char {
        val sorted = this.groupBy { it }
        return if (sorted['0']?.size == sorted['1']?.size) def
        else sorted.minByOrNull { it.value.size }!!.key
    }
}
