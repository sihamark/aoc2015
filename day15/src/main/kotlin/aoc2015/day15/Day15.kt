package aoc2015.day15

import aoc2015.utility.allPermutations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
object Day15 {

    fun highestCookieScore(): Int = runBlocking {
        val ingredients = input.map { Parser.parse(it) }

        val recipes = mutableListOf<Recipe>()
        (0..100).toList().allPermutations(ingredients.size) { rawRecipe ->
            val recipe = Recipe(rawRecipe.mapIndexed { index, i ->
                CountedIngredient(i, ingredients[index])
            })
            recipes.add(recipe)
        }
        recipes.filter { it.numberOfIngredients == 100 }
                .map { it.calculateScore() }
                .max()!!
    }

    private data class Recipe(
            val ingredients: List<CountedIngredient>
    ) {
        val numberOfIngredients: Int
            get() = ingredients.map { it.amount }.sum()

        fun calculateScore() = sumProperty { it.capacity } *
                sumProperty { it.durability } *
                sumProperty { it.flavor } *
                sumProperty { it.texture }

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