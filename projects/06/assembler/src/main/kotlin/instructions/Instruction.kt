package instructions

interface HackInstruction {
    companion object {
        fun matchWith(code: String): HackInstruction {
            return when {
                InstructionPattern.A.matches(code) -> InstructionA(code)
                InstructionPattern.C.matches(code) -> InstructionC(code)
                else -> throw Exception("Code doesn't match any instruction.")
            }
        }
    }

    fun translate(): String
}
