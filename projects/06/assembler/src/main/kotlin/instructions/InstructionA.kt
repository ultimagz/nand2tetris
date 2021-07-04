package instructions

import extensions.toBinaryString
import symbols.define.BuildInSymbols
import symbols.define.LabelSymbols
import symbols.define.VariableSymbols

class InstructionA(
    private val code: String,
    private val labelSymbols: LabelSymbols
): HackInstruction {

    private val variableSymbols = VariableSymbols()

    override fun translate(): String {
        return InstructionPattern.A.matchEntire(code)
            ?.groupValues
            ?.lastOrNull()
            ?.let { symbol ->
                getSymbolValue(symbol)
            } ?: throw Exception("Fail to translate ($code) to Instruction A.")
    }

    private fun getSymbolValue(symbol: String): String {
        return symbol.toIntOrNull()?.toBinaryString() ?: getValueFromSymbol(symbol)
    }

    private fun getValueFromSymbol(symbol: String): String {
        val value = BuildInSymbols.lookUp(symbol)
            ?: labelSymbols.lookUp(symbol)
            ?: variableSymbols.lookUp(symbol)
        return value.toBinaryString()
    }
}
