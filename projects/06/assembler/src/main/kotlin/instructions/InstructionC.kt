package instructions

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

                val dest = getDestSymbolValue(destSymbol)
                val a = getAControlBit(compSymbol)
                val comp = getCompSymbolValue(compSymbol)
                val jump = "000"

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

                val dest = "000"
                val a = getAControlBit(compSymbol)
                val comp = getCompSymbolValue(compSymbol)
                val jump = getJumpSymbolValue(jumpSymbol)

                "111$a$comp$dest$jump"
            } ?: throw Exception("Fail to translate ($code) to Instruction C Comp;Jump.")
    }

    private fun getDestSymbolValue(symbol: String): String {
        return when(symbol) {
            "M"   -> "001"
            "D"   -> "010"
            "MD"  -> "011"
            "A"   -> "100"
            "AM"  -> "101"
            "AD"  -> "110"
            "AMD" -> "111"
            else  -> "000"
        }
    }

    private fun getAControlBit(symbol: String): String {
        return if (symbol.contains("M")) { "1" } else { "0" }
    }

    private fun getCompSymbolValue(symbol: String): String {
        return when(symbol) {
            "0"          -> "101010"
            "1"          -> "111111"
            "-1"         -> "111010"
            "D"          -> "001100"
            "A", "M"     -> "110000"
            "!D"         -> "001101"
            "!A", "!M"   -> "110001"
            "-D"         -> "001111"
            "-A", "-M"   -> "110011"
            "D+1"        -> "011111"
            "A+1", "M+1" -> "110111"
            "D-1"        -> "001110"
            "A-1", "M-1" -> "110010"
            "D+A", "D+M" -> "000010"
            "D-A", "D-M" -> "010011"
            "A-D", "M-D" -> "000111"
            "D&A", "D&M" -> "000000"
            "D|A", "D|M" -> "010101"
            else  -> "000000"
        }
    }

    private fun getJumpSymbolValue(symbol: String): String {
        return when(symbol) {
            "JGT" -> "001"
            "JEQ" -> "010"
            "JGE" -> "011"
            "JLT" -> "100"
            "JNE" -> "101"
            "JLE" -> "110"
            "JMP" -> "111"
            else  -> "000"
        }
    }
}