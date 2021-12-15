class Q13(filename: String) : Question(filename) {

    private val dots: Map<Pair<Int, Int>, Boolean>
    private val folds: List<Pair<Int, Int>>

    init {
        readInput().let {
            dots = it.subList(0, it.indexOf(""))
                .map { dot -> dot.split(",").let { d -> Pair(d[0].toInt(), d[1].toInt()) to true } }.toMap()
            folds = it.subList(it.indexOf("") + 1, it.size).map { fold ->
                fold.substringAfter("fold along ").split("=")
                    .let { f -> if (f[0] == "y") Pair(-1, f[1].toInt()) else Pair(f[1].toInt(), -1) }
            }
        }
    }

    private fun MutableMap<Pair<Int, Int>, Boolean>.fold(fold: Pair<Int, Int>) {
        if (fold.first >= 0) {
            filterKeys { it.first == fold.first }.forEach { remove(it.key) }
            filterKeys { it.first > fold.first }.forEach {
                remove(it.key)
                val newDot = it.key.copy(first = it.key.first - (2 * (it.key.first - fold.first)))
                put(newDot, true)
            }
        } else {
            filterKeys { it.second == fold.second }.forEach { remove(it.key) }
            filterKeys { it.second > fold.second }.forEach {
                remove(it.key)
                val newDot = it.key.copy(second = it.key.second - (2 * (it.key.second - fold.second)))
                put(newDot, true)
            }
        }
    }

    fun partA(): Int = dots.toMutableMap().apply { fold(folds.first()) }.size
    fun partB(): String {
        val paper = dots.toMutableMap().apply {
            folds.forEach { fold(it) }
        }
        val maxX = paper.keys.maxByOrNull { it.first }!!.first
        val maxY = paper.keys.maxByOrNull { it.second }!!.second
        val output = (0..maxY).map { y ->
            (0..maxX).joinToString(separator = "") { x ->
                if (paper.containsKey(Pair(x, y))) "#" else " "
            }
        }
        return output.joinToString(separator = "\n")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q13 = Q13("q13.txt".toInput())
            println(q13.partA())
            println(q13.partB())
        }
    }

}