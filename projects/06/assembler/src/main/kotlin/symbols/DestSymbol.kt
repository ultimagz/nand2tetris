package symbols

class DestSymbol private constructor() {
    companion object {
        const val none: String = "000"

        fun getValue(symbol: String): String {
            return when(symbol) {
                "M"   -> "001"
                "D"   -> "010"
                "MD"  -> "011"
                "A"   -> "100"
                "AM"  -> "101"
                "AD"  -> "110"
                "AMD" -> "111"
                else  -> none
            }
        }
    }
}