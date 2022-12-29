package aoc2015.day18

object Day18 {

    private val originalGrid: Grid = Parser.parse()
    val inputGrid: Grid
        get() = Grid(originalGrid)

    fun amountOfLightsTurnedOnAfter100Steps(): Int {
        var grid = inputGrid

        repeat(100) {
            grid++
        }

        return grid.amountOfTurnedOnLights()
    }

    fun amountOfLightsTurnedOnAfter100StepsAndOnCorners(): Int {
        var grid = inputGrid
        grid.turnOnCorners()

        repeat(100) {
            grid++
            grid.turnOnCorners()
        }

        return grid.amountOfTurnedOnLights()
    }

    private object Parser {
        fun parse(): Grid {
            val lightValues = input.split("\n")
                    .mapIndexed { x, row ->
                        row.mapIndexed { y, c ->
                            Position(x, y) to (c == '#')
                        }
                    }.flatten()
                    .associate { it }
            return Grid(lightValues)
        }
    }
}