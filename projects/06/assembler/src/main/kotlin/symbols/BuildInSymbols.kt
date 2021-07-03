package symbols

class BuildInSymbols private constructor() {
    companion object {
        fun getValue(symbol: String): Int? {
            return when(symbol) {
                "R0" -> 0
                "R1" -> 1
                "R2" -> 2
                "R3" -> 3
                "R4" -> 4
                "R5" -> 5
                "R6" -> 6
                "R7" -> 7
                "R8" -> 8
                "R9" -> 9
                "R10" -> 10
                "R11" -> 11
                "R12" -> 12
                "R13" -> 13
                "R14" -> 14
                "R15" -> 15
                "SCREEN" -> 16384
                "KBD" -> 24576

                "SP" -> 0
                "LCL" -> 1
                "ARG" -> 2
                "THIS" -> 3
                "THAT" -> 4
                else -> null
            }
        }

        fun isBuildInSymbol(symbol: String): Boolean {
            return when(symbol) {
                "R0",
                "R1",
                "R2",
                "R3",
                "R4",
                "R5",
                "R6",
                "R7",
                "R8",
                "R9",
                "R10",
                "R11",
                "R12",
                "R13",
                "R14",
                "R15",
                "SCREEN",
                "KBD",
                "SP",
                "LCL",
                "ARG",
                "THIS",
                "THAT" -> true
                else -> false
            }
        }
    }
}