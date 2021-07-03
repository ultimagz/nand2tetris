import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import instructions.HackInstruction
import java.io.File

fun main(args: Array<String>) = Assembler().main(args)

class Assembler: CliktCommand(help = "Translate FILE Hack assembly to binary.") {

    private val file by argument()
    private val customOutPath: String? = null

    override fun run() {
        File(file).readLines()
            .asSequence()
            .map { CleanUp(it).removeComments() }
            .filter { it.isNotEmpty() }
            .map { echo(it); it }
            .map { HackInstruction.matchWith(it).translate() }
            .toList()
            .run {
                val outFile = createOutputFile(customOutPath)
                saveToHackFile(outFile, this)
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