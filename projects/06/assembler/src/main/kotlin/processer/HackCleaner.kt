package processer

class HackCleaner {
    fun removeComments(line: String): String {
        return line.replace(regex = Regex("//.*"), replacement = "").trim()
    }
}