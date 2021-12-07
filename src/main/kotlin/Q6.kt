class Q6(filename: String) : Question(filename) {

    private val inputs = readInput().first().split(",").map { it.toInt() }

    private fun compute(days: Int): Long = inputs.sumOf { computeFish(days, it) }

    private fun computeFish(days: Int, initTimer: Int): Long {
        var count: Long = 1
        val firstSpawnDay = days - (initTimer + 1)
        if (firstSpawnDay >= 0) {
            count += computeFish(firstSpawnDay, 8)
            if (firstSpawnDay >= 7) {
                repeat(firstSpawnDay / 7) {
                    count += computeFish(firstSpawnDay - ((it + 1) * 7), 8)
                }
            }
        }
        return count
    }

    fun partA() = compute(80)
    fun partB() = compute(256)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q6 = Q6("q6.txt".toInput())
            println(q6.partA()) // 345793
            println(q6.partB()) //takes 50 minutes to compute 1572643095893

        }
    }

}