import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import instructions.HackInstruction
import output.HackOutput
import symbols.define.LabelSymbols
import java.io.File

fun main(args: Array<String>) = HackAssembler().main(args)

class HackAssembler: CliktCommand(help = "Translate FILE Hack assembly to binary.") {

    private val file by argument()
    private val labelSymbols = LabelSymbols()

    override fun run() {
        File(file).readLines()
            .map { removeComments(it) }
            .filter { it.isNotEmpty() }
            .let { labelSymbols.parseLabelSymbols(it) }
            .map { HackInstruction.matchWith(it, labelSymbols = labelSymbols).translate() }
            .run { HackOutput().save(inputFile = file, data = this) }
    }

    private fun removeComments(line: String): String {
        return line.replace(regex = Regex("//.*"), replacement = "").trim()
    }
}