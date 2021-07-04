package symbols.define

class LabelSymbols {
    private val labelPattern = "^(?>\\(([a-zA-Z_.\$:][a-zA-Z0-9_.\$:]*)\\))\$".toRegex()
    private val labelSymbols: HashMap<String, Int> = hashMapOf()

    fun parseLabelSymbols(data: List<String>): List<String> {
        val newData: ArrayList<String> = arrayListOf()
        var lineCount = 0

        data.forEach { line ->
            getLabelSymbol(line)
                ?.run {
                    labelSymbols[this] = lineCount
                } ?: run {
                lineCount += 1
                newData.add(line)
            }
        }

        return newData
    }

    fun lookUp(symbol: String): Int? {
        return labelSymbols[symbol]
    }

    private fun getLabelSymbol(line: String): String? {
        return labelPattern.matchEntire(line)
            ?.groupValues
            ?.toMutableList()
            ?.getOrNull(1)
    }
}