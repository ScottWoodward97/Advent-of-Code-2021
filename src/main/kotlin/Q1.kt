class Q1(fileName: String) : Question(fileName) {

    private val inputs: List<Int> = readInput().map { it.toInt(10) }

    private fun partA(measurements: List<Int> = inputs): Int =
        (1..measurements.lastIndex).sumBy { index -> if (measurements[index] > measurements[index - 1]) 1 else 0 }

    private fun partB(): Int =
        partA((2..inputs.lastIndex).map { index -> inputs[index] + inputs[index - 1] + inputs[index - 2] })

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val question = Q1("q1.txt".toInput())
            println(question.partA())
            println(question.partB())
        }
    }

}