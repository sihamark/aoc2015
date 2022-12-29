package aoc2015.day8

import javax.script.ScriptEngineManager

object Day8 {

    fun solvePart1() = Part1.solveIt()
    fun solvePart2() = Part2.solveIt()

    object Part1 {

        fun solveIt() = input
                .map { it.countInCode() - it.countInMemory() }
                .sum()

        private fun String.countInCode() = this.length
        private fun String.countInMemory() = Counter.countInMemory(this)

        object Counter {
            private val engine = ScriptEngineManager().getEngineByName("js")

            fun countInMemory(string: String): Int =
                    (engine.eval(string) as String?)?.length ?: error("impossible to evaluate $string")
        }
    }

    object Part2 {

        fun solveIt() = input
                .map { it.countEncoded() - it.countOriginal() }
                .sum()

        private fun String.countEncoded() = this.encode().length
        private fun String.countOriginal() = this.length

        private fun String.encode() = replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .let { "\"$it\"" }
    }
}