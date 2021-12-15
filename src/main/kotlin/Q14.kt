class Q14(filename: String) : Question(filename) {

    private val initTemplate: String
    private val insertionRules: Map<String, String>

    init {
        readInput().let { input ->
            initTemplate = input.first()
            insertionRules =
                input.subList(2, input.size).map { rule -> rule.split(" -> ").let { Pair(it[0], it[1]) } }.toMap()
        }
    }

    private fun findCharDiff(iterate: Int): Long{
        val charCount: MutableMap<String, Long> = initTemplate
            .groupBy { it.toString() }
            .map { Pair(it.key, it.value.size.toLong()) }
            .toMap().toMutableMap()
        val pairs: MutableMap<String, Long> = (0 until initTemplate.lastIndex)
            .map { ind -> initTemplate.substring(ind, ind + 2) }
            .groupBy { it }
            .map { Pair(it.key, it.value.size.toLong()) }
            .toMap().toMutableMap()
        repeat(iterate){
            pairs.toMap().forEach { (pair, count) ->
                if (count > 0){
                    val char = insertionRules[pair]!!
                    pairs[pair[0] + char] = pairs.getOrDefault(pair[0] + char, 0) + count
                    pairs[char + pair[1]] = pairs.getOrDefault(char + pair[1], 0) + count
                    pairs[pair]?.let { pairs[pair] = it - count }
                    charCount[char] = charCount.getOrDefault(char, 0) + count
                }
            }
        }
        return charCount.values.sorted().let { it.last() - it.first() }
    }

    fun partA(): Long = findCharDiff(10)
    fun partB(): Long = findCharDiff(40)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q14 = Q14("q14.txt".toInput())
            println(q14.partA())
            println(q14.partB())
        }
    }

}