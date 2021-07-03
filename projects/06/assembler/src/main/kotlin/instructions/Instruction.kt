package instructions

interface HackInstruction {
    companion object {
        fun matchWith(
            code: String,
            labelSymbols: HashMap<String, Int> = hashMapOf(),
            variableSymbols: HashMap<String, Int> = hashMapOf()
        ): HackInstruction {
            return when {
                InstructionPattern.A.matches(code) -> InstructionA(code, labelSymbols = labelSymbols, variableSymbols = variableSymbols)
                InstructionPattern.C.matches(code) -> InstructionC(code)
                else -> throw Exception("Code doesn't match any instruction.")
            }
        }
    }

    fun translate(): String
}
