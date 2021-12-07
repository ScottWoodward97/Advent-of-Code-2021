import kotlin.math.abs

class Q7(filename: String) : Question(filename) {

    private val inputs: List<Int> = readInput().first().split(",").map { it.toInt() }

    fun partA(): Int = (inputs.minOrNull()!!..inputs.maxOrNull()!!).map { position ->
        inputs.sumBy { abs(it - position) }
    }.minOrNull() ?: -1

    fun partB(): Int {
        fun sumGap(steps: Int): Int = (steps * (1 + steps)) / 2

        return (inputs.minOrNull()!!..inputs.maxOrNull()!!).map { position ->
            inputs.sumBy { sumGap(abs(it - position)) }
        }.minOrNull() ?: -1
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q7 = Q7("q7.txt".toInput())
            println(q7.partA())
            println(q7.partB())
        }
    }

}