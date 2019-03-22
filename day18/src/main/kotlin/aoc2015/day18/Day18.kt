package aoc2015.day18


object Day18 {

    val inputGrid = Parser.parse()

    fun amountOfLightsTurnedOnAfter100Steps(): Int {
        var grid = inputGrid

        (1..100).forEach {
            grid++
        }

        return grid.amountOfTurnedOnLights()
    }

    fun amountOfLightsTurnedOnAfter100StepsAndOnCorners(): Int {
        var grid = inputGrid
        grid.turnOnCorners()

        (1..100).forEach {
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