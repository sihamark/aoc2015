package day8

import kotlin.test.assertEquals
import javax.script.ScriptEngineManager

fun main() {
    Part2.solveIt()
}

object Part2 {
    private val examples = listOf(
            """""""",
            """"abc"""",
            """"aaa\"aaa"""",
            """"\x27""""
    )

    fun solveIt() {
        examples[0].testCount(6, 2)
        examples[1].testCount(9, 5)
        examples[2].testCount(16, 10)
        examples[3].testCount(11, 6)

        input.map { it.countEncoded() - it.countOriginal() }
                .sum()
                .let { println("result: $it") }
        input.fold(0 to 0) { (inCode, inMemory), s -> (inCode + s.countEncoded()) to (inMemory + s.countOriginal()) }
                .also { (inCode, inMemory) -> println("inCode: $inCode, inMemory: $inMemory") }
                .let { (inCode, inMemory) -> inCode - inMemory }
                .also { result -> println("result is: $result") }
    }

    private fun String.countEncoded() = this.encode().length
    private fun String.countOriginal() = this.length

    private fun String.encode() = replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .let { "\"$it\"" }

    private fun String.testCount(encoded: Int, original: Int) {
        assertEquals(
                encoded,
                this.countEncoded(),
                "encoded count of $this should be $encoded, encoded: ${this.encode()}"
        )
        assertEquals(original, this.countOriginal(), "original count of $this should be $original")
    }
}