package output

import java.io.File

class HackOutput() {
    companion object {
        const val HACK_FILE_EXTENSION = ".hack"
    }

    fun save(inputFile: String, data: List<String>) {
        val outFile = createOutputFile(inputFile)
        saveToHackFile(outFile, data)
    }

    private fun createOutputFile(inputFile: String): File {
        val parentDir = File(inputFile).parent
        val fileName = File(inputFile).nameWithoutExtension

        return File("$parentDir/$fileName$HACK_FILE_EXTENSION")
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