package aoc2015.day15

object Day15 {

    fun highestCookieScore(): Int {
        TODO("implement")
    }


    private data class Ingredient(
            val name: String,
            val capacity: Int,
            val durability: Int,
            val flavor: Int,
            val texture: Int,
            val calories: Int
    )

    private object Parser {

        private val validIngredient= Regex("""(\w+): capacity (\d+), durability (\d+), flavor (\d+), texture (\d+), calories (\d+)""")

        fun parse(rawIngredient: String): Ingredient {
            TODO("implement")
        }

    }
}