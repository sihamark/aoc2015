package aoc2015.day19

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

object Day19 {

    private val replacements = rawReplacements.map { Parser.parseReplacement(it) }

    fun numberOfDistinctMoleculesAfterOneReplacement() =
            replacements.flatMap { it.doReplace(medicine) }
                    .distinct()
                    .size

    fun findFewestStepsToFabricateMedicine(): Int = runBlocking {
        val counter = AtomicInteger(0)
        val start = System.currentTimeMillis()
        val steps = async {
            println("begin fabrication")
            fabricate(medicine, counter, "e")
        }

        launch {
            while (isActive) {
                delay(5_000)
                val time = System.currentTimeMillis() - start
                println("did ${counter.get()} replacements in $time ms")
            }
        }

        launch {
            println("stop fabrication in 5 seconds")
            delay(60_000)
            println("stopping fabrication")
            try {
                steps.cancelAndJoin()
            } finally {
                println("did ${counter.get()} replacements")
            }
        }

        steps.await()
    }

    private suspend fun fabricate(target: String, counter: AtomicInteger, current: String, steps: Int = 0): Int {
        yield()

        val currentReplacements = replacements.flatMap { it.doReplace(current) }
                .distinct()
                .filter { it.length <= target.length }

        counter.incrementAndGet()
        val newStep = steps + 1

        return when {
            currentReplacements.any { it == target } -> newStep
            currentReplacements.isEmpty() -> Integer.MAX_VALUE
            else -> {
                var min = Integer.MAX_VALUE
                for (replacement in currentReplacements) {
                    val newSteps = fabricate(target, counter, replacement, newStep)
                    if (newSteps < min) {
                        min = newSteps
                    }
                }
                min
            }
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