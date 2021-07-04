package instructions

import symbols.define.LabelSymbols

interface HackInstruction {
    companion object {
        fun matchWith(
            code: String,
            labelSymbols: LabelSymbols
        ): HackInstruction {
            return when {
                InstructionPattern.A.matches(code) -> InstructionA(code, labelSymbols = labelSymbols)
                InstructionPattern.C.matches(code) -> InstructionC(code)
                else -> throw Exception("Code doesn't match any instruction.")
            }
        }
    }

    fun translate(): String
}
