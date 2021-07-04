package instructions

import extensions.toBinaryString
import symbols.define.BuildInSymbols
import symbols.define.LabelSymbols
import symbols.define.VariableSymbols

class InstructionA(
    private val code: String,
    private val labelSymbols: HashMap<String, Int> = hashMapOf(),
    private val variableSymbols: HashMap<String, Int> = hashMapOf()
): HackInstruction {
    override fun translate(): String {
        return InstructionPattern.A.matchEntire(code)
            ?.groupValues
            ?.lastOrNull()
            ?.let { symbol ->
                getSymbolValue(symbol)
            } ?: throw Exception("Fail to translate ($code) to Instruction A.")
    }

    private fun getSymbolValue(symbol: String): String {
        return symbol.toIntOrNull()?.let { toBinaryString(it) } ?: getValueFromSymbol(symbol)
    }

    private fun getValueFromSymbol(symbol: String): String {
        val value = variableSymbols[symbol]
            ?: labelSymbols[symbol]
            ?: BuildInSymbols.getValue(symbol)
            ?: throw Exception(" Cannot find symbol ($symbol)")
        return toBinaryString(value)
    }

    fun toBinaryString(x: Int, len: Int = 16): String {
        return String.format(
            "%${len}s",
            Integer.toBinaryString(x)
        ).replace(" ".toRegex(), "0")
    }
}
