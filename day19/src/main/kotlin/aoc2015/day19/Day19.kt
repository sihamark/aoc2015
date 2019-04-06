package aoc2015.day19

object Day19 {

    private val replacements = rawReplacements.map { Parser.parseReplacement(it) }

    fun numberOfDistinctMoleculesAfterOneReplacement() =
            replacements.flatMap { it.doReplace(medicine) }
                    .distinct()
                    .size

    fun findFewestStepsToFabricateMedicine(): Int {
        return fabricate(medicine, 100, "e")
    }

    private fun fabricate(target: String, maxSteps: Int = 100, current: String, steps: Int = 0): Int {
        val currentReplacements = replacements.flatMap { it.doReplace(current) }
        val newStep = steps + 1
        return when {
            currentReplacements.any { it == target } -> newStep
            newStep >= maxSteps -> Integer.MAX_VALUE
            else -> currentReplacements.map {
                fabricate(target, maxSteps, it, newStep)
            }.min() ?: error("no min found")
        }
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
    ) {
        fun doReplace(medicine: String) = Regex(source).findAll(medicine)
                .map { medicine.replaceRange(it.range, replacement) }
                .distinct()
                .toList()
    }
}