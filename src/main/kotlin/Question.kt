import java.io.File

abstract class Question(private val fileName: String) {

    fun readInput(): List<String> = File(fileName).readLines()

}

fun String.toInput() = "./src/main/resources/inputs/$this"
