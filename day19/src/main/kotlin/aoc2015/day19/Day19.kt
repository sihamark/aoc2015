package aoc2015.day19

object Day19 {

    fun numberOfDistinctMoleculesAfterOneReplacement(): Int {
        replacements.map { Parser.parseReplacement(it) }
        TODO("do the replacements")
    }

    private object Parser {
        private val validReplacement = Regex("""(\w+) => (\w+)""")

        fun parseReplacement(rawReplacement: String): Replacement {
            val values = validReplacement.find(rawReplacement)?.groupValues
                    ?: error("invalid replacement: $rawReplacement")
            return Replacement(values[1], values[2])
        }
    }

    private data class Replacement(
            val source: String,
            val replacement: String
    )
}