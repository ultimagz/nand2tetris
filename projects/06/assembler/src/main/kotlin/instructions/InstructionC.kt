package instructions

import symbols.instruction.ABit
import symbols.instruction.CompSymbols
import symbols.instruction.DestSymbols
import symbols.instruction.JumpSymbols

// D = M+1; JGT -> 111 1110111 010 001

private data class CPart(
    val dest: String? = null,
    val comp: String? = null,
    val jump: String? = null,
)

class InstructionC(private val code: String): HackInstruction {
    override fun translate(): String {
        return when {
            InstructionPattern.C_DEST_COMP.matches(code) ->
                translate(code, InstructionPattern.C_DEST_COMP, ::mappingDestComp)
            InstructionPattern.C_COMP_JUMP.matches(code) ->
                translate(code, InstructionPattern.C_COMP_JUMP, ::mappingCompJump)
            InstructionPattern.C_FULL.matches(code) ->
                translate(code, InstructionPattern.C_FULL, ::mappingFull)
            else -> throw Exception("Cannot translate ($code) to Instruction A format.")
        }
    }

    private fun translate(
        code: String,
        pattern: InstructionPattern,
        mapping: (List<String>) -> CPart
    ): String {
        return getInstructionParts(pattern, code)
            ?.let { mapping(it) }
            ?.run { buildMachineCode(this) }
            ?: throw Exception("Fail to translate ($code) to $pattern")
    }

    private fun mappingFull(data: List<String>): CPart {
        assert(data.size == 3)
        return CPart(
            dest = data.getOrNull(0),
            comp = data.getOrNull(1),
            jump = data.getOrNull(2)
        )
    }

    private fun mappingDestComp(data: List<String>): CPart {
        assert(data.size == 2)
        return CPart(dest = data.first(), comp = data.last())
    }

    private fun mappingCompJump(data: List<String>): CPart {
        assert(data.size == 2)
        return CPart(comp = data.first(), jump = data.last())
    }

    private fun getInstructionParts(pattern: InstructionPattern, code: String): List<String>? {
        return pattern.matchEntire(code)
            ?.groupValues
            ?.toMutableList()
            ?.let {
                it.removeFirst()
                it
            }
    }

    private fun buildMachineCode(cPart: CPart): String {
        val dest = DestSymbols.lookUp(cPart.dest)
        val a = ABit.lookUp(cPart.comp)
        val comp = CompSymbols.lookUp(cPart.comp)
        val jump = JumpSymbols.lookUp(cPart.jump)

        return "111$a$comp$dest$jump"
    }
}