class Q2(fileName: String) : Question(fileName) {

    private val inputs = readInput().map { input ->
        input.split(" ").let {
            val instruction = Instruction.valueOf(it[0].toUpperCase())
            Pair(instruction, it[1].toInt())
        }
    }

    fun partA(): Int {
        var distance = 0
        var depth = 0
        inputs.forEach {
            when (it.first) {
                Instruction.FORWARD -> distance += it.second
                Instruction.DOWN -> depth += it.second
                Instruction.UP -> depth -= it.second
            }
        }
        return distance * depth
    }

    fun partB(): Int {
        var distance = 0
        var depth = 0
        var aim = 0
        inputs.forEach {
            when (it.first) {
                Instruction.FORWARD -> {
                    distance += it.second
                    depth += aim * it.second
                }
                Instruction.DOWN -> {
                    aim += it.second
                }
                Instruction.UP -> {
                    aim -= it.second
                }
            }
        }
        return distance * depth
    }

    enum class Instruction { FORWARD, UP, DOWN }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q2 = Q2("q2.txt".toInput())
            println(q2.partA())
            println(q2.partB())
        }
    }
}