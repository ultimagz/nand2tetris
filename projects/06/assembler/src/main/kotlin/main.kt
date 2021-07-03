import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import java.io.File

fun main(args: Array<String>) = Assembler().main(args)

class Assembler: CliktCommand(help = "Translate FILE Hack assembly to binary.") {

    private val file by argument()

    override fun run() {
        File(file).readLines()
            .map { removeComments(it) }
            .forEach { echo(it) }
    }

    internal fun removeComments(line: String): String {
        return line.replace(regex = Regex("//.*"), replacement = "").trim()
    }
}