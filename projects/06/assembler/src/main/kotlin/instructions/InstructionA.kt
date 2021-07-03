package instructions

import com.github.ajalt.clikt.output.TermUi

class InstructionA(private val code: String): HackInstruction {
    override fun translate(): String {
        return InstructionPattern.A.matchEntire(code)
            ?.groupValues
            ?.lastOrNull()
            ?.let { symbol ->
                getSymbolValue(symbol)
            } ?: throw Exception("Cannot translate ($code) to Instruction A format.")
    }

    private fun getSymbolValue(symbol: String): String {
        return symbol.toIntOrNull()?.let { toBinary(it) } ?: getValueFromSymbolTable(symbol)
    }

    private fun getValueFromSymbolTable(symbol: String): String {
        return "0000 0000 0000 0000"
    }

    fun toBinary(x: Int, len: Int = 16): String {
        return String.format(
            "%${len}s",
            Integer.toBinaryString(x)
        ).replace(" ".toRegex(), "0")
    }

}
