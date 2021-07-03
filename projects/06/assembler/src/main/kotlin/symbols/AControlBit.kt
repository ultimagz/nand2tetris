package symbols

class AControlBit private constructor() {
    companion object {
        fun getValue(symbol: String): String {
            return if (symbol.contains("M")) { "1" } else { "0" }
        }
    }
}