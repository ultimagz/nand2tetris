package symbols.instruction

class AControlBit private constructor() {
    companion object {
        private val INSTANCE = AControlBit()

        fun lookUp(symbol: String): String = INSTANCE.lookUp(symbol)
    }

    fun lookUp(symbol: String): String {
        return if (symbol.contains("M")) { "1" } else { "0" }
    }
}