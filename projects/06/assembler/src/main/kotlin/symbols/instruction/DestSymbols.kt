package symbols

class DestSymbols private constructor() {
    companion object {
        const val NONE: String = "000"

        private val INSTANCE = DestSymbols()

        fun lookUp(symbol: String): String = INSTANCE.lookUp(symbol)
    }

    fun lookUp(symbol: String): String {
        return when(symbol) {
            "M"   -> "001"
            "D"   -> "010"
            "MD"  -> "011"
            "A"   -> "100"
            "AM"  -> "101"
            "AD"  -> "110"
            "AMD" -> "111"
            else  -> NONE
        }
    }
}