import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import java.io.File

fun main(args: Array<String>) = Assembler().main(args)

class Assembler: CliktCommand(help = "Translate FILE Hack assembly to binary.") {

    private val file by argument()

    private val aPattern = "^@(.*)$".toRegex()
    private val cDest = "([AMD]|[AM]D|AM|AMD)"
    private val cComp = "([01]|-[1AMD]|!?[AMD]|[AMD][+-]1|D[-+&|][AM]|[AM]-D)?"
    private val cJump = "(JGT|JEQ|JGE|JLT|JNE|JLE|JMP)"
    private val cPattern = "^(?>$cDest=)?$cComp?$(?>$cJump;)?".toRegex()

    override fun run() {
        File(file).readLines()
            .map { CleanUp(it).removeComments() }
            .filter { it.isNotEmpty() }
            .map { handlingInstructions(it) }
            .forEach { echo(it) }
    }

    private fun handlingInstructions(line: String): String {
        return when {
            aPattern.matches(line) -> translateInstructionA(line)
            cPattern.matches(line) -> translateInstructionC(line)
            else -> line
        }
    }

    private fun translateInstructionA(line: String): String  {
        aPattern.matchEntire(line)?.groupValues?.toMutableList()?.let { values ->
            values.removeFirstOrNull()
            values
                .filter { it.isNotEmpty() }
                .forEach {
                    echo("      $it")
                }
        }
        return "InstructionA $line"
    }

    private fun translateInstructionC(line: String): String  {
        cPattern.matchEntire(line)?.groupValues?.toMutableList()?.let { values ->
            values.removeFirstOrNull()
            values
                .filter { it.isNotEmpty() }
                .forEach {
                    echo("      $it")
                }
        }
        return "InstructionC $line"
    }
}