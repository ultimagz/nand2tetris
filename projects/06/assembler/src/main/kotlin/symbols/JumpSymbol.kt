package symbols

class JumpSymbol private constructor() {
    companion object {
        const val none: String = "000"

        fun getValue(symbol: String): String {
            return when(symbol) {
                "JGT" -> "001"
                "JEQ" -> "010"
                "JGE" -> "011"
                "JLT" -> "100"
                "JNE" -> "101"
                "JLE" -> "110"
                "JMP" -> "111"
                else  -> none
            }
        }
    }
}