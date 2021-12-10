class Q10(filename: String) : Question(filename) {

    private val inputs = readInput()

    private val validPairs = mapOf('[' to ']', '{' to '}', '(' to ')', '<' to '>')
    private val chunkRegex = Regex("(\\{})|(<>)|(\\[])|(\\(\\))")

    private val Char.syntaxErrorScore: Int
        get() = when (this) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }

    private val Char.autoCompleteScore: Long
        get() = when (this) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> 0
        }

    fun partA(): Int = inputs.sumBy {
        var chunks = it
        while (chunkRegex.containsMatchIn(chunks)) {
            chunks = chunkRegex.replace(chunks, "")
        }
        chunks.firstOrNull { char -> validPairs.containsValue(char) }?.syntaxErrorScore ?: 0
    }

    fun partB(): Long = inputs.mapNotNull {
        var chunks = it
        while (chunkRegex.containsMatchIn(chunks)) {
            chunks = chunkRegex.replace(chunks, "")
        }
        chunks.takeIf { s -> s.all { char -> validPairs[char] != null } }
    }.map { input ->
        input.mapNotNull { validPairs[it]?.autoCompleteScore }.reduceRight { l, acc -> (acc * 5) + l }
    }.sorted().let { it[it.size / 2] }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q10 = Q10("q10.txt".toInput())
            println(q10.partA())
            println(q10.partB())
        }
    }

}