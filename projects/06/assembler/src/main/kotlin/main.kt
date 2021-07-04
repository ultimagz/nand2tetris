import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import instructions.HackInstruction
import output.HackOutput
import processer.HackCleaner
import symbols.define.LabelSymbols
import java.io.File

fun main(args: Array<String>) = HackAssembler().main(args)

class HackAssembler: CliktCommand(help = "Translate FILE Hack assembly to binary.") {

    private val file by argument()
    private val labelSymbols = LabelSymbols()
    private val cleaner = HackCleaner()

    override fun run() {
        File(file).readLines()
            .map { cleaner.removeComments(it) }
            .filter { it.isNotEmpty() }
            .let { labelSymbols.parseLabelSymbols(it) }
            .map { HackInstruction.matchWith(it, labelSymbols = labelSymbols).translate() }
            .run { HackOutput().save(inputFile = file, data = this) }
    }
}