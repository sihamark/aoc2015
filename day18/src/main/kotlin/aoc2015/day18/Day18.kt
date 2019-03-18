package aoc2015.day18


object Day18 {

    fun amountOfLightsTurnedOnAfter100Steps(): Int {
        var grid = Parser.parse()

        (1..100).forEach {
            grid++
        }

        return grid.amountOfTurnedOnLights()
    }

    private object Parser {
        fun parse() = Grid().apply {
            input.split("\n")
                    .forEachIndexed { x, row ->
                        row.forEachIndexed { y, c ->
                            this[x, y] = c == '#'
                        }
                    }
        }
    }

    private data class Position(
            val x: Int,
            val y: Int
    )

    private class Grid {
        private val lights = mutableMapOf<Position, Boolean>()

        operator fun set(x: Int, y: Int, isOn: Boolean) {
            lights[Position(x, y)] = isOn
        }

        operator fun get(x: Int, y: Int) = lights[Position(x, y)] ?: false

        operator fun get(position: Position) = lights[position] ?: false

        fun amountOfTurnedOnLights() = lights.values.count { it }

        override fun toString() = buildString {
            (0..99).forEach { x ->
                (0..99).forEach { y ->
                    append(if (this@Grid[x, y]) '#' else '.')
                }
                append('\n')
            }
        }

        operator fun inc(): Grid {
            val nextGrid = Grid()
            this.forEach { x, y, isOn ->
                val adjacentOn = adjacent(x, y)
                        .map { this[it] }
                        .count { it }
                nextGrid[x, y] = if (isOn) {
                    adjacentOn == 2 || adjacentOn == 3
                } else {
                    adjacentOn == 3
                }
            }
            return nextGrid
        }

        private fun forEach(block: (x: Int, y: Int, isOn: Boolean) -> Unit) {
            (0..99).forEach { x ->
                (0..99).forEach { y ->
                    block(x, y, this[x, y])
                }
            }
        }

        companion object {
            private fun adjacent(x: Int, y: Int): List<Position> = listOf(
                    Position(x, y + 1),
                    Position(x, y - 1),
                    Position(x + 1, y),
                    Position(x + 1, y + 1),
                    Position(x + 1, y - 1),
                    Position(x - 1, y),
                    Position(x - 1, y + 1),
                    Position(x - 1, y - 1)
            )
        }
    }
}