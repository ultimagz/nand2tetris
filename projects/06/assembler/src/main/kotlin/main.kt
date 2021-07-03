import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import instructions.HackInstruction
import java.io.File

fun main(args: Array<String>) = Assembler().main(args)

class Assembler: CliktCommand(help = "Translate FILE Hack assembly to binary.") {

    private val file by argument()

    override fun run() {
        File(file).readLines()
            .map { CleanUp(it).removeComments() }
            .filter { it.isNotEmpty() }
            .map { HackInstruction.matchWith(it).translate() }
            .forEach { echo(it) }
    }
}