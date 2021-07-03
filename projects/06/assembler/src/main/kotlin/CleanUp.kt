class CleanUp(private val line: String) {
    internal fun removeComments(): String {
        return line.replace(regex = Regex("//.*"), replacement = "").trim()
    }
}