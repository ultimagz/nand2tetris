package instructions

import symbols.instruction.ABit
import symbols.instruction.CompSymbols
import symbols.instruction.DestSymbols
import symbols.instruction.JumpSymbols

class InstructionC(private val code: String): HackInstruction {
    override fun translate(): String {
        return when {
            InstructionPattern.C_DEST_COMP.matches(code) -> translate(InstructionPattern.C_DEST_COMP, ::buildDestComp)
            InstructionPattern.C_COMP_JUMP.matches(code) -> translate(InstructionPattern.C_COMP_JUMP, ::buildCompJump)
            else -> throw Exception("Cannot translate ($code) to Instruction A format.")
        }
    }

    private fun translate(pattern: InstructionPattern, transform: (Pair<String, String>) -> String): String {
        return pattern.matchEntire(code)
            ?.groupValues
            ?.toMutableList()
            ?.let {
                it.removeFirst()
                assert(it.size == 2)
                Pair(it.first(), it.last())
            }
            ?.let(transform)
            ?: throw Exception("Cannot translate ($code) to $pattern.")
    }

    private fun buildDestComp(symbols: Pair<String, String>): String {
        val destSymbol = symbols.first
        val compSymbol = symbols.second
        return buildMachineCode(destSymbol = destSymbol, compSymbol = compSymbol)
    }

    private fun buildCompJump(symbols: Pair<String, String>): String {
        val compSymbol = symbols.first
        val jumpSymbol = symbols.second
        return buildMachineCode(compSymbol = compSymbol, jumpSymbol = jumpSymbol)
    }

    private fun buildMachineCode(destSymbol: String? = null, compSymbol: String, jumpSymbol: String? = null): String {
        val dest = if (destSymbol == null) DestSymbols.NONE else DestSymbols.lookUp(destSymbol)
        val a = ABit.lookUp(compSymbol)
        val comp = CompSymbols.lookUp(compSymbol)
        val jump = if (jumpSymbol == null) JumpSymbols.NONE else JumpSymbols.lookUp(jumpSymbol)

        return "111$a$comp$dest$jump"
    }
}