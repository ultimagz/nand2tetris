package symbols

class CompSymbol private constructor() {
    companion object {
        const val none: String = "000000"

        fun getValue(symbol: String): String {
            return when(symbol) {
                "0"          -> "101010"
                "1"          -> "111111"
                "-1"         -> "111010"
                "D"          -> "001100"
                "A", "M"     -> "110000"
                "!D"         -> "001101"
                "!A", "!M"   -> "110001"
                "-D"         -> "001111"
                "-A", "-M"   -> "110011"
                "D+1"        -> "011111"
                "A+1", "M+1" -> "110111"
                "D-1"        -> "001110"
                "A-1", "M-1" -> "110010"
                "D+A", "D+M" -> "000010"
                "D-A", "D-M" -> "010011"
                "A-D", "M-D" -> "000111"
                "D&A", "D&M" -> "000000"
                "D|A", "D|M" -> "010101"
                else  -> none
            }
        }
    }
}