package aoc2015.day19

object Day19 {

    fun numberOfDistinctMoleculesAfterOneReplacement() {

    }

    private object Parser {
        private val validReplacement = Regex("""(\w+) => \w+""")

        fun parseReplacement(rawReplacement: String): Replacement {
            TODO("implement")
        }
    }

    private data class Replacement(
            val source: String,
            val replacement: String
    )
}