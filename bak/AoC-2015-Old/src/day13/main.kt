package day13

import utility.allPermutations

fun main() {
    val effects = Effects(input.map { Parser.parse(it) })

    calculateHighestEffectWithoutYou(effects)

    calculateHighestEffectWithYou(effects)
}

fun calculateHighestEffectWithYou(effects: Effects) {
    val allSeatingArrangements = (effects.persons + Effects.YOU)
            .allPermutations()
            .map { SeatingArrangement(it) }

    allSeatingArrangements.map { it.calculateHappiness(effects) }
            .max()
            .also { println("highest happiness with you is $it") }
}

private fun calculateHighestEffectWithoutYou(effects: Effects) {
    val allSeatingArrangements = effects.persons.allPermutations()
            .map { SeatingArrangement(it) }

    allSeatingArrangements.map { it.calculateHappiness(effects) }
            .max()
            .also { println("highest happiness without you is $it") }
}

data class SeatingEffect(
        val person: String,
        val neighbor: String,
        val effect: Int
)

data class SeatingArrangement(
        private val persons: List<String>
) {
    private fun get(index: Int): String {
        val adjusted = index.rem(persons.size)
                .let { if (it >= 0) it else persons.size + it }
        return persons[adjusted]
    }

    fun calculateHappiness(effects: Effects): Int = persons.mapIndexed { index: Int, person: String ->
        effects[person, get(index - 1)] + effects[person, get(index + 1)]
    }.sum()
}

class Effects(
        private val effects: List<SeatingEffect>
) {
    val persons
        get() = effects.flatMap { listOf(it.person, it.neighbor) }.distinct()

    operator fun get(person: String, neighbor: String) = if (person == YOU || neighbor == YOU) {
        0
    } else {
        effects
                .find { it.person == person && it.neighbor == neighbor }
                ?.effect ?: error("did not find effect for person $person and neighbor $neighbor")
    }

    companion object {
        const val YOU = "you"
    }
}

object Parser {
    private val validEffect = Regex("""(\w+) would (gain|lose) (\d+) happiness units by sitting next to (\w+)\.""")

    fun parse(rawEffect: String): SeatingEffect {
        val parsedValues = validEffect.find(rawEffect)?.groupValues ?: error("invalid effect: $rawEffect")

        val signum = if (parsedValues[2] == "gain") 1 else -1

        return SeatingEffect(
                parsedValues[1],
                parsedValues[4],
                signum * parsedValues[3].toInt()
        )
    }
}
