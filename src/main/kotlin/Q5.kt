class Q5(filename: String): Question(filename) {

    private val inputs: List<VentLine> = readInput().map { line ->
        line.split(" -> ").map { points ->
            points.split(",").map { it.toInt() }
        }.let {
            VentLine(it[0].toPair(), it[1].toPair())
        }
    }
    private fun List<Int>.toPair() = Pair(get(0), get(1))

    data class VentLine(
        val startPoint: Pair<Int, Int>,
        val endPoint: Pair<Int, Int>
    ){
        val isDiagonal: Boolean = startPoint.first != endPoint.first && startPoint.second != endPoint.second

        fun getAllPointsOnLine(): List<Pair<Int, Int>> = when {
            isDiagonal -> toZippedIntRange(startPoint, endPoint)
            startPoint.first == endPoint.first -> toIntRange(startPoint.second, endPoint.second).map { Pair(startPoint.first, it) }
            else -> toIntRange(startPoint.first, endPoint.first).map { Pair(it, startPoint.second) }
        }

        private fun toIntRange(a: Int, b: Int) = if (a < b) (a..b) else (a downTo b)
        private fun toZippedIntRange(a: Pair<Int, Int>, b: Pair<Int, Int>): List<Pair<Int, Int>> =
            toIntRange(a.first, b.first).toList().zip(toIntRange(a.second, b.second))
    }

    private fun getNumOverlappingVents(includeDiagonal: Boolean): Int{
        val plane: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
        val lines = if (includeDiagonal) inputs else inputs.filterNot { it.isDiagonal }
        lines.forEach {
            it.getAllPointsOnLine().forEach { vent ->
                plane[vent] = (plane[vent] ?: 0) + 1
            }
        }
        return plane.values.count { it > 1 }
    }

    fun partA(): Int = getNumOverlappingVents(includeDiagonal = false)
    fun partB(): Int = getNumOverlappingVents(includeDiagonal = true)

    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            val q5 = Q5("q5.txt".toInput())
            println(q5.partA())
            println(q5.partB())
        }
    }

}