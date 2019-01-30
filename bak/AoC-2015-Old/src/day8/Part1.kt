package day8

import kotlin.test.assertEquals
import javax.script.ScriptEngineManager

fun main() {
    Part1.solveIt()
}

object Part1 {
    private val patternHexChar = Regex("\\\\x([0-9|a-f]{2})")
    private val examples = listOf(
            """""""",
            """"abc"""",
            """"aaa\"aaa"""",
            """"\x27""""
    )

    fun solveIt() {
        examples[0].testCount(2, 0)
        examples[1].testCount(5, 3)
        examples[2].testCount(10, 7)
        examples[3].testCount(6, 1)

        input.fold(0 to 0) { (inCode, inMemory), s -> (inCode + s.countInCode()) to (inMemory + s.countInMemory()) }
                .also { (inCode, inMemory) -> println("inCode: $inCode, inMemory: $inMemory") }
                .let { (inCode, inMemory) -> inCode - inMemory }
                .also { result -> println("result is: $result") }
    }

    fun String.countInCode() = this.length
    fun String.countInMemory() = Counter.countInMemory(this)

    fun String.unescape() = this.removeSurrounding("\"")
            .replace("""\"""", "\"")
            .replace("""\\""", """\""").let { string ->
                val replaces = mutableListOf<Pair<IntRange, Char>>()
                patternHexChar.findAll(string).forEach {
                    val char = it.groupValues[1].toInt(16).toChar()
                    replaces += it.range to char
                }
                var resultString = string
                replaces.asReversed().forEach { (range, char) ->
                    resultString = resultString.replaceRange(range, char.toString())
                }
                resultString
            }

    private fun String.testCount(inCode: Int, inMemory: Int) {
        assertEquals(inCode, this.countInCode(), "in code count of $this should be $inCode")
        assertEquals(inMemory, this.countInMemory(), "in memory count of $this should be $inMemory")
    }

    object Counter {
        private val engine = ScriptEngineManager().getEngineByName("js")

        fun countInMemory(string: String): Int =
                (engine.eval(string) as String?)?.length ?: error("impossible to evaluate $string")
    }
}