import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import instructions.HackInstruction
import symbols.define.LabelSymbols
import java.io.File

fun main(args: Array<String>) = HackAssembler().main(args)

class HackAssembler: CliktCommand(help = "Translate FILE Hack assembly to binary.") {

    private val file by argument()
    private val fileExtension = ".hack"
    private val labelSymbols = LabelSymbols()

    override fun run() {
        File(file).readLines()
            .map { removeComments(it) }
            .filter { it.isNotEmpty() }
            .let { labelSymbols.parseLabelSymbols(it) }
            .map { HackInstruction.matchWith(it, labelSymbols = labelSymbols).translate() }

            .run { save(this) }
    }

    private fun removeComments(line: String): String {
        return line.replace(regex = Regex("//.*"), replacement = "").trim()
    }

    private fun save(data: List<String>) {
        val outFile = createOutputFile()
        saveToHackFile(outFile, data)
    }

    private fun createOutputFile(): File {
        val parentDir = File(file).parent
        val fileName = File(file).nameWithoutExtension

        return File("$parentDir/$fileName$fileExtension")
    }

    private fun saveToHackFile(file: File, data: List<String>) {
        if (!file.exists()) {
            file.createNewFile()
        }

        file.printWriter().use { writer ->
            data.forEach {
                writer.println(it)
            }
        }
    }
}