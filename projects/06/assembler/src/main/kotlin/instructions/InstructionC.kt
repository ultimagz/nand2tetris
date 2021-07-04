package instructions

import symbols.instruction.ABit
import symbols.instruction.CompSymbols
import symbols.instruction.DestSymbols
import symbols.instruction.JumpSymbols

class InstructionC(private val code: String): HackInstruction {
    override fun translate(): String {
        return when {
            InstructionPattern.C_DEST_COMP.matches(code) -> translateDestComp(code)
            InstructionPattern.C_COMP_JUMP.matches(code) -> translateCompJump(code)
            else -> throw Exception("Cannot translate ($code) to Instruction A format.")
        }
    }

    private fun translateDestComp(code: String): String {
        return InstructionPattern.C_DEST_COMP.matchEntire(code)
            ?.groupValues
            ?.toMutableList()
            ?.let {
                it.removeFirst()

                val destSymbol = it.first()
                val compSymbol = it.last()

                val dest = DestSymbol.getValue(destSymbol)
                val a = AControlBit.getValue(compSymbol)
                val comp = CompSymbol.getValue(compSymbol)
                val jump = JumpSymbol.none

                "111$a$comp$dest$jump"
            } ?: throw Exception("Fail to translate ($code) to Instruction C Dest=Comp.")
    }

    private fun translateCompJump(code: String): String {
        return InstructionPattern.C_COMP_JUMP.matchEntire(code)
            ?.groupValues
            ?.toMutableList()
            ?.let {
                it.removeFirst()

                val compSymbol = it.first()
                val jumpSymbol = it.last()

                val dest = DestSymbol.none
                val a = AControlBit.getValue(compSymbol)
                val comp = CompSymbol.getValue(compSymbol)
                val jump = JumpSymbol.getValue(jumpSymbol)

                "111$a$comp$dest$jump"
            } ?: throw Exception("Fail to translate ($code) to Instruction C Comp;Jump.")
    }
}