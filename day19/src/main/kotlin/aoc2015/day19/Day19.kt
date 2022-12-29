package aoc2015.day19

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

object Day19 {

    private val replacements = rawReplacements.map { Parser.parseReplacement(it) }

    fun numberOfDistinctMoleculesAfterOneReplacement() =
            replacements.flatMap { it.replaceAll(medicine) }
                    .distinct()
                    .size

    fun findFewestStepsToFabricateMedicine(): Int {
        val inverseReplacements = replacements.map { it.inverse() }
        var count: Int? = null

        while (count == null) {
            count = tryToReduceMolecule(medicine, inverseReplacements)
        }

        return count
    }

    private fun tryToReduceMolecule(molecule: String, replacements: List<Replacement>): Int? {
        var count = 0
        var currentMolecule = molecule
        val sources = replacements.map { it.source }

        while (currentMolecule != "e") {
            val randomReplacement = replacements.random()

            if (sources.all { !currentMolecule.contains(it) }) {
                println("can't reduce $currentMolecule any further abort")
                return null
            }

            if (currentMolecule.contains(randomReplacement.source)) {
                currentMolecule = randomReplacement.replaceFirst(currentMolecule)
                count++
            }
        }

        return count
    }

    private fun fabrication(): Int = runBlocking {
        val counter = AtomicInteger(0)
        val latest = AtomicReference("")
        val start = System.currentTimeMillis()

        val steps = async {
            println("begin fabrication target is ${medicine.length} long")
            fabricate(medicine, counter, latest, listOf(), "e")
        }

        launch {
            while (isActive) {
                delay(5_000)
                val time = System.currentTimeMillis() - start
                val latestValue = latest.get()
                println("did ${counter.get()} replacements in $time ms latest(${latestValue.length}): $latestValue")
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

    private suspend fun fabricate(target: String, counter: AtomicInteger, latest: AtomicReference<String>, already: List<String>, current: String, steps: Int = 0): Int {
        yield()

        val newAlready = already.toMutableList().apply {
            add(current)
        }

        latest.set(current)

        val currentReplacements = replacements.flatMap { it.replaceAll(current) }
                .distinct()
                .filter { it.length <= target.length }
                .filter { !newAlready.contains(it) }

        counter.incrementAndGet()
        val newStep = steps + 1

        return when {
            current == target -> steps
            currentReplacements.any { it == target } -> newStep
            currentReplacements.isEmpty() -> Integer.MAX_VALUE
            else -> {
                var min = Integer.MAX_VALUE
                for (replacement in currentReplacements) {
                    val newSteps = fabricate(target, counter, latest, newAlready, replacement, newStep)
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
        fun replaceFirst(molecule: String) = molecule.replaceFirst(source, replacement)

        fun replaceAll(medicine: String) = Regex(source).findAll(medicine)
                .map { medicine.replaceRange(it.range, replacement) }
                .distinct()
                .toList()

        fun inverse() = Replacement(replacement, source)
    }
}