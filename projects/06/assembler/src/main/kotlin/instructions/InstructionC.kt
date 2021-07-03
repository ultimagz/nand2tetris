package instructions

class InstructionC(private val code: String): HackInstruction {
    override fun translate(): String {
        return when {
            InstructionPattern.C_DEST_COMP.matches(code) -> translateDestComp(code)
            InstructionPattern.C_DEST_JUMP.matches(code) -> translateDestJump(code)
            InstructionPattern.C_COMP_JUMP.matches(code) -> translateCompJump(code)
            else -> throw Exception("Cannot translate ($code) to Instruction A format.")
        }
    }

    private fun translateDestComp(code: String): String {
        return "InstructionC Dest=Comp ($code)"
    }

    private fun translateDestJump(code: String): String {
        return "InstructionC Dest;Jump ($code)"
    }

    private fun translateCompJump(code: String): String {
        return "InstructionC Comp;Jump ($code)"
    }
}