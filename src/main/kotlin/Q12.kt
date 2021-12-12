class Q12(filename: String) : Question(filename) {

    private val connections: Map<String, List<String>> =
        readInput().flatMap { cave -> cave.split("-").let { listOf(Pair(it[0], it[1]), Pair(it[1], it[0])) } }
            .groupBy { it.first }.map { (name, connections) -> Pair(name, connections.map { it.second }) }.toMap()

    private fun String.isLargeCave() = all { it.isUpperCase() }

    private fun traverse(path: List<String>, caveFilter: (String, List<String>) -> Boolean): List<List<String>> {
        val remainingCaves = connections.filterKeys { caveFilter(it, path) }
        val currentCave = path.last()
        return if (currentCave == "end") listOf(path)
        else connections[currentCave]!!.filter { remainingCaves[it] != null }
            .flatMap { traverse(path + it, caveFilter) }
    }

    fun partA(): Int {
        fun caveFilter(cave: String, path: List<String>) =
            cave.isLargeCave() || !path.subList(0, path.lastIndex).contains(cave)
        return traverse(listOf("start"), ::caveFilter).size
    }

    fun partB(): Int {
        fun caveFilter(cave: String, path: List<String>): Boolean {
            val hasVisitedSmallTwice = path.filterNot { it.isLargeCave() }.let { it.size != it.distinct().size }
            return cave.isLargeCave() ||
                    (cave == "start" && path.last() == cave) ||
                    cave != "start" && (
                    path.subList(0, path.lastIndex).contains(cave).not()
                            || (path.subList(0, path.lastIndex).contains(cave) && hasVisitedSmallTwice.not())
                    )
        }
        return traverse(listOf("start"), ::caveFilter).size
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q12 = Q12(("q12.txt").toInput())
            println(q12.partA())
            println(q12.partB())
        }
    }
}