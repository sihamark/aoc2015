package aoc2015.day15


object Day15 {

    fun highestCookieScore() = recipes()
            .map { it.calculateScore() }
            .max()!!

    fun highestCookieScoreWith500Calories() = recipes()
            .filter { it.calculateCalories() == 500 }
            .map { it.calculateScore() }
            .max()!!

    private fun recipes(): Sequence<Recipe> {
        val ingredients = input.map { Parser.parse(it) }

        return CounterIterator((0..100).toList(), 4)
                .asSequence()
                .filter { it.sum() == 100 }
                .map {
                    Recipe(it.mapIndexed { index, amount ->
                        CountedIngredient(amount, ingredients[index])
                    })
                }
    }

    private data class Recipe(
            val ingredients: List<CountedIngredient>
    ) {
        fun calculateScore() = sumProperty { it.capacity } *
                sumProperty { it.durability } *
                sumProperty { it.flavor } *
                sumProperty { it.texture }

        fun calculateCalories() = sumProperty { it.calories }

        private fun sumProperty(property: (Ingredient) -> Int): Int =
                ingredients.map { it.amount * property(it.ingredient) }.sum().coerceAtLeast(0)
    }

    private data class Ingredient(
            val name: String,
            val capacity: Int,
            val durability: Int,
            val flavor: Int,
            val texture: Int,
            val calories: Int
    )

    private data class CountedIngredient(
            val amount: Int,
            val ingredient: Ingredient
    )

    private class CounterIterator<T>(private val elements: List<T>, places: Int) : Iterator<List<T>> {

        private var currentIndices = List(places) { 0 }
        private var hasNext = true

        override fun next(): List<T> {
            val result = currentIndices.map { elements[it] }

            currentIndices = currentIndices.next()

            return result
        }

        override fun hasNext(): Boolean = hasNext

        private fun List<Int>.next(): List<Int> {
            val next = this.toMutableList()
            for (index in (size - 1) downTo 0) {
                val inc = next[index] + 1
                if (inc >= elements.size) {
                    if (index == 0) {
                        hasNext = false
                    }
                    next[index] = 0
                } else {
                    next[index] = inc
                    return next
                }
            }
            return next
        }
    }

    private object Parser {

        private val validIngredient = Regex("""(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""")

        fun parse(rawIngredient: String): Ingredient {
            val parsedValues = validIngredient.find(rawIngredient)?.groupValues
                    ?: error("no valid ingredient: $rawIngredient")
            return Ingredient(
                    parsedValues[1],
                    parsedValues[2].toInt(),
                    parsedValues[3].toInt(),
                    parsedValues[4].toInt(),
                    parsedValues[5].toInt(),
                    parsedValues[6].toInt()
            )
        }

    }
}