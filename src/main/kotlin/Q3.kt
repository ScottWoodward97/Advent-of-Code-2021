import kotlin.math.max
import kotlin.math.sign

class Q3(filename: String) : Question(filename) {

    private val inputs: List<String> = readInput()

    fun partA(): Int =
        inputs.map {
            it.map { bit -> if (bit == '1') 1 else -1 }
        }.reduce { acc, list ->
            acc.mapIndexed { index, i -> i + list[index] }
        }.map {
            max(it.sign, 0)
        }.let { list ->
            val gamma = list.joinToString(separator = "").toInt(2)
            val epsilon = list.map { if (it == 1) 0 else 1 }.joinToString(separator = "").toInt(2)
            gamma * epsilon
        }

    fun partB(): Int {
        val oxygen = filterBit(inputs, Predicate.MAX).first()
        val co2 = filterBit(inputs, Predicate.MIN).first()
        return oxygen.toInt(2) * co2.toInt(2)
    }

    private fun filterBit(list: List<String>, predicate: Predicate, index: Int = 0): List<String> =
        if (list.size == 1) list
        else filterBit(
            list.partition { it[index] == '1' }.let {
                when (predicate) {
                    Predicate.MAX -> if (it.first.size >= it.second.size) it.first else it.second
                    Predicate.MIN -> if (it.first.size >= it.second.size) it.second else it.first
                }
            },
            predicate,
            index + 1
        )

    enum class Predicate { MIN, MAX }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q3 = Q3("q3.txt".toInput())
            println(q3.partA())
            println(q3.partB())
        }
    }

}