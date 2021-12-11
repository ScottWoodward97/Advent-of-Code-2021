class Q11(filename: String) : Question(filename) {

    private val input: Map<Pair<Int, Int>, Pair<Int, Boolean>> = readInput().flatMapIndexed { rowIndex, row ->
        row.mapIndexed { intIndex, int -> Pair(rowIndex, intIndex) to Pair(int.toString().toInt(), false) }
    }.toMap()

    fun partA(): Int {
        val state = input.toMutableMap()
        var flashes = 0
        repeat(100) { flashes += step(state) }
        return flashes
    }

    fun partB(): Int{
        val state = input.toMutableMap()
        var step = 0
        do {
            step++
        } while (step(state) != state.size)
        return step
    }

    private fun step(state: MutableMap<Pair<Int, Int>, Pair<Int, Boolean>>): Int {
        fun flashCheck() {
            state.forEach { (pair, oct) ->
                if (oct.first > 9 && oct.second.not()) {
                    pair.getSurrounding().forEach { p -> state[p]?.let { state[p] = it.copy(first = it.first + 1) } }
                    state[pair] = oct.copy(second = true)
                    flashCheck()
                }
            }
        }
        state.forEach { (pair, oct) -> state[pair] = oct.copy(first = oct.first + 1) }
        flashCheck()
        return state.count { (_, i) -> i.second}.also {
            state.forEach { (pair, i) -> if (i.second) state[pair] = Pair(0, false) }
        }
    }

    private fun Pair<Int, Int>.getSurrounding(): List<Pair<Int, Int>> = listOf(
        Pair(first + 1, second),
        Pair(first - 1, second),
        Pair(first, second + 1),
        Pair(first, second - 1),
        Pair(first + 1, second + 1),
        Pair(first + 1, second - 1),
        Pair(first - 1, second + 1),
        Pair(first - 1, second - 1),
    )

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q11 = Q11("q11.txt".toInput())
            println(q11.partA())
            println(q11.partB())
        }
    }

}