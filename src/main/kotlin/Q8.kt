class Q8(filename: String) : Question(filename) {

    private val inputs: List<Signals> = readInput().map {
        it.split(" | ").let {
            Signals(it.first().split(" "), it[1].split(" "))
        }
    }

    data class Signals(val input: List<String>, val output: List<String>)

    fun partA(): Int = inputs.sumOf {
        it.output.count { signal ->
            signal.length == 2 || signal.length == 3 || signal.length == 4 || signal.length == 7
        }
    }

    fun partB(): Int = inputs.sumOf { findOutputCode(it) }

    private fun findOutputCode(signal: Signals): Int {
        val nums = mutableMapOf<String, Int>()
        with(signal.input.groupBy { it.length }) {
            nums[get(2)!!.first().order()] = 1
            nums[get(3)!!.first().order()] = 7
            nums[get(4)!!.first().order()] = 4
            nums[get(7)!!.first().order()] = 8

            get(6)!!.let {
                val (six, notSix) = it.partition { segment -> !segment.contains(get(2)!!.first()) }
                nums[six.first().order()] = 6
                val (zero, nine) = notSix.partition { segment -> !segment.contains(get(4)!!.first()) }
                nums[zero.first().order()] = 0
                nums[nine.first().order()] = 9
            }

            get(5)!!.let {
                val (three, notThree) = it.partition { segment -> segment.contains(get(2)!!.first()) }
                nums[three.first().order()] = 3
                val (five, two) = notThree.partition { segment ->
                    (segment.toList() - get(4)!!.first().toList()).size < 3
                }
                nums[five.first().order()] = 5
                nums[two.first().order()] = 2
            }
        }
        return signal.output.map { nums[it.order()] }.joinToString(separator = "").toInt()
    }

    private fun String.order() = toCharArray().sorted().joinToString(separator = "")
    private fun String.contains(chars: String): Boolean = toList().containsAll(chars.toList())

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q8 = Q8("q8.txt".toInput())
            println(q8.partA())
            println(q8.partB())
        }
    }

}