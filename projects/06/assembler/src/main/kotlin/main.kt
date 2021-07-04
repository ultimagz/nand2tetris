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

    }

    private fun removeComments(line: String): String {
        return line.replace(regex = Regex("//.*"), replacement = "").trim()
    }

    }


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

    private fun createOutputFile(customOutPath: String?): File {
        val outPath = if (customOutPath.isNullOrEmpty()) {
            val parentDir = File(file).parent
            val fileName = File(file).nameWithoutExtension
            val fileExtension = ".hack"

            "$parentDir/$fileName$fileExtension"
        } else {
            customOutPath
        }

        return File(outPath)
    }
}