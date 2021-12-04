class Q4(filename: String) : Question(filename) {

    private val callNumbers: List<Int>
    private val cards: List<BingoCard>

    init {
        readInput().filterNot { it.isEmpty() }.let {
            callNumbers = it.first().split(",").map { num -> num.toInt() }
            cards = (1..it.lastIndex step 5).map { ind ->
                val grid =
                    it[ind].toIntList() + it[ind + 1].toIntList() + it[ind + 2].toIntList() + it[ind + 3].toIntList() + it[ind + 4].toIntList()
                BingoCard(grid)
            }
        }
    }

    private fun String.toIntList() = split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

    class BingoCard(private val values: List<Int>) {
        var isWinnable: Boolean = false
            private set
        var score: Int = 0
            private set

        private val checkedValues: MutableMap<Int, Boolean> = mutableMapOf(
            *values.map { value ->
                value to false
            }.toTypedArray()
        )

        fun call(number: Int) {
            checkedValues[number]?.let {
                checkedValues[number] = true
                isWinnable = hasWon()
                if (isWinnable) score = getScore(number)
            }
        }

        private fun getScore(lastCalledNumber: Int): Int =
            checkedValues.filterValues { !it }.keys.sum() * lastCalledNumber

        private fun hasWon(): Boolean {
            val completeRow = values.chunked(5).any { row ->
                row.all { checkedValues[it] == true }
            }
            return if (completeRow) true else
                values.groupBy { values.indexOf(it) % 5 }.values.any { col ->
                    col.all { checkedValues[it] == true }
                }
        }
    }

    fun partA(): Int {
        callNumbers.forEach { num ->
            cards.forEach { it.call(num) }
            val score = cards.filter { it.isWinnable }.map { it.score }
            if (score.isNotEmpty()) return score.first()
        }
        return -1
    }

    fun partB(): Int {
        var unWonCards = cards
        callNumbers.forEach { num ->
            unWonCards.forEach { it.call(num) }
            unWonCards.filter { !it.isWinnable }.let {
                if (it.isEmpty()) return unWonCards.first().score
                unWonCards = it
            }
        }
        return -1
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q4 = Q4("q4.txt".toInput())
            println(q4.partA())
            println(q4.partB())
        }
    }

}