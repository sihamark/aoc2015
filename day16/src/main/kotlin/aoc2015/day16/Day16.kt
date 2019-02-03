package aoc2015.day16

object Day16 {

    private val targetSueProperties = listOf(
            "children" to 3,
            "cats" to 7,
            "samoyeds" to 2,
            "pomeranians" to 3,
            "akitas" to 0,
            "vizslas" to 0,
            "goldfish" to 5,
            "trees" to 3,
            "cars" to 2,
            "perfumes" to 1
    ).map { Property(it) }

    fun findNumberOfSue(): Int {
        val validSues = sues()
                .filter { targetSueProperties.containsAll(it.properties) }

        return validSues.first().number
    }

    fun findRealNumberOfSue(): Int {
        val validSues = sues()
                .filter { sue ->
                    sue.properties.all { property ->
                        val targetProperty = targetSueProperties.find { it.name == property.name }!!
                        when (property.name) {
                            "pomeranians" -> property.amount < targetProperty.amount
                            "goldfish" -> property.amount < targetProperty.amount
                            "cats" -> property.amount > targetProperty.amount
                            "trees" -> property.amount > targetProperty.amount
                            else -> property.amount == targetProperty.amount
                        }
                    }
                }

        return validSues.first().number
    }

    private fun sues() = input.asSequence()
            .map { Parser.parse(it) }

    private data class Sue(
            val number: Int,
            val properties: List<Property>
    ) {
        constructor(number: Int, vararg properties: Pair<String, Int>) : this(number, properties.map { Property(it.first, it.second) })
    }

    private data class Property(
            val name: String,
            val amount: Int
    ) {
        constructor(property: Pair<String, Int>) : this(property.first, property.second)
    }

    private object Parser {
        private val validSue = Regex("""Sue (\d+): (\w+): (\d+), (\w+): (\d+), (\w+): (\d+)""")

        fun parse(rawSue: String): Sue {
            val parsedValues = validSue.find(rawSue)?.groupValues ?: error("supplied raw sue was invalid: $rawSue")

            return Sue(
                    parsedValues[1].toInt(),
                    parsedValues[2] to parsedValues[3].toInt(),
                    parsedValues[4] to parsedValues[5].toInt(),
                    parsedValues[6] to parsedValues[7].toInt()
            )
        }
    }
}
