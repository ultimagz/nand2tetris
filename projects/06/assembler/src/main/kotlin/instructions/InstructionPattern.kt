package instructions

enum class InstructionPattern(internal val pattern: String) {
    C_DEST("([AMD]|[AM]D|AM|AMD)"),
    C_COMP("([01]|-[1AMD]|!?[AMD]|[AMD][+-]1|D[-+&|][AM]|[AM]-D)?"),
    C_JUMP("(JGT|JEQ|JGE|JLT|JNE|JLE|JMP)"),
    C_DEST_COMP("^(?>${C_DEST.pattern}=)${C_COMP.pattern}$"),
    C_COMP_JUMP("^${C_COMP.pattern}(?>;${C_JUMP.pattern})$"),
    C_FULL("^(?>${C_DEST.pattern}=)${C_COMP.pattern}(?>;${C_JUMP.pattern})$"),
    C("^(?>${C_DEST.pattern}=)?${C_COMP.pattern}?(?>;${C_JUMP.pattern})?$"),
    A("^@(.*)$");

    fun matches(code: String): Boolean = pattern.toRegex().matches(code)

    fun matchEntire(code: String): MatchResult? = pattern.toRegex().matchEntire(code)
}