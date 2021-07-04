package symbols.define

class VariableSymbols {
    private val variablePattern = "^(?>@([a-zA-Z_.\$:][a-zA-Z0-9_.\$:]*))\$".toRegex()
    private val variableSymbols: LinkedHashMap<String, Int> = linkedMapOf()
    private var address = 16

    fun lookUp(symbol: String): Int {
        return variableSymbols[symbol] ?: addSymbol(symbol)
    }

    private fun addSymbol(symbol: String): Int {
        variableSymbols[symbol] = address
        return address.also { address += 1 }
    }
}