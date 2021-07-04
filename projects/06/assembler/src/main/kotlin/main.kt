import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import instructions.HackInstruction
import symbols.define.LabelSymbols
import java.io.File

fun main(args: Array<String>) = HackAssembler().main(args)

class HackAssembler: CliktCommand(help = "Translate FILE Hack assembly to binary.") {

    private val file by argument()
    private val customOutPath: String? = null
    private val labelSymbols: HashMap<String, Int> = hashMapOf()
    private val variableSymbols: HashMap<String, Int> = hashMapOf()

    override fun run() {
        File(file).readLines()
            .map { CleanUp(it).removeComments() }
            .filter { it.isNotEmpty() }
            .let { collectLabelSymbols(it) }
            .let { collectVariableSymbols(it) }
            .map { echo(it); it }
            .map { HackInstruction.matchWith(it, labelSymbols = labelSymbols, variableSymbols = variableSymbols).translate() }
            .toList()
            .run {
                debugUserDefineSymbols()
                val outFile = createOutputFile(customOutPath)
                saveToHackFile(outFile, this)
            }
    }

    private fun debugUserDefineSymbols() {
        echo()
        echo("labels = $labelSymbols")
        echo()
        echo("variables = $variableSymbols")
    }

    private fun collectLabelSymbols(data: List<String>): List<String> {
        val labelPattern = "^(?>\\(([a-zA-Z_.\$:][a-zA-Z0-9_.\$:]*)\\))\$".toRegex()
        val newData: ArrayList<String> = arrayListOf()
        var lineCount = 0
        data.forEach { line ->
            labelPattern.matchEntire(line)
                ?.groupValues
                ?.toMutableList()
                ?.let {
                    it.removeFirst()

                    val label = it.first()
                    labelSymbols[label] = lineCount
                } ?: run {
                    lineCount += 1
                    newData.add(line)
                }
        }

        return newData
    }

    private fun collectVariableSymbols(data: List<String>): List<String>  {
        val variablePattern = "^(?>@([a-zA-Z_.\$:][a-zA-Z0-9_.\$:]*))\$".toRegex()
        var address = 16
        data.forEach { line ->
            variablePattern.matchEntire(line)
                ?.groupValues
                ?.toMutableList()
                ?.let {
                    it.removeFirst()
                    it
                }
                ?.filter {
                    variableSymbols[it] == null && labelSymbols[it] == null && !BuildInSymbols.isBuildInSymbol(it)
                }
                ?.forEach {
                    variableSymbols[it] = address
                    address += 1
                }
        }

        return data
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