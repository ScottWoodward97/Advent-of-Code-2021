class Q9(filename: String) : Question(filename) {

    private val plane: Map<Pair<Int, Int>, Int> = readInput().mapIndexed { rowIndex, row ->
        row.map { it.toString().toInt() }.mapIndexed { colIndex, value -> Pair(rowIndex, colIndex) to value }
    }.toMap()

    private fun <K, V> List<List<Pair<K, V>>>.toMap(): Map<K, V> = mutableMapOf(*flatten().toTypedArray())

    private fun Pair<Int, Int>.getAdjacent(): List<Pair<Int, Int>> = listOf(
        Pair(first, second + 1),
        Pair(first, second - 1),
        Pair(first + 1, second),
        Pair(first - 1, second)
    )

    private fun findLowPoints() = plane.filter { (coord, value) ->
        coord.getAdjacent().all { c -> plane[c]?.let { it > value } ?: true }
    }.keys.toList()

    private fun findBasinSize(lowPoint: Pair<Int, Int>): Int {
        val explored = mutableSetOf(lowPoint)
        fun rec(point: Pair<Int, Int>): Int = point.getAdjacent()
            .filter { plane.containsKey(it) && !explored.contains(it) }
            .also { explored.addAll(it) }
            .filterNot { plane[it] == 9 }
            .takeIf { it.isNotEmpty() }
            ?.sumBy { rec(it) + 1 } ?: 0
        return rec(lowPoint) + 1
    }

    fun partA(): Int = findLowPoints()
        .mapNotNull { plane[it] }
        .sumBy { it + 1 }

    fun partB(): Int = findLowPoints()
        .map { findBasinSize(it) }
        .sortedDescending()
        .take(3)
        .reduce { acc, i -> acc * i }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q9 = Q9("q9.txt".toInput())
            println(q9.partA())
            println(q9.partB())
        }
    }

}