package aoc2015.day2

/**
 * [https://adventofcode.com/2015/day/2]
 */
object Day2 {

    fun calculateNeededSurface() =
            input.map { parseDimensions(it) }
                    .map { it.surface() }
                    .sum()

    fun calculateNeededRibbon() =
            input.map { parseDimensions(it) }
                    .map { it.volume() + it.shortestCircumference() }
                    .sum()

    private fun parseDimensions(rawDimensions: String) =
            rawDimensions.split("x").let {
                Box(it[0].toInt(), it[1].toInt(), it[2].toInt())
            }

    data class Box(
            val length: Int,
            val width: Int,
            val height: Int
    ) {
        fun surface(): Int {
            val side1 = length * width
            val side2 = width * height
            val side3 = height * length

            return 2 * side1 + 2 * side2 + 2 * side3 + min(side1, side2, side3)
        }

        fun shortestCircumference(): Int {
            val shortest = min(length, width, height)
            val dimensions = mutableListOf(length, width, height)
            dimensions -= shortest
            val secondShortest = min(*dimensions.toIntArray())
            return shortest * 2 + secondShortest * 2
        }

        fun volume() = length * width * height
    }

    private tailrec fun min(vararg args: Int): Int = when {
        args.isEmpty() -> error("you need to supply at least one argument")
        args.size == 1 -> args.first()
        args.size == 2 -> Math.min(args[0], args[1])
        else -> min(Math.min(args[0], args[1]), *(args.drop(2).toIntArray()))
    }
}