package aoc2015.day3

/**
 * [https://adventofcode.com/2015/day/3]
 */
object Day3 {

    fun countHouses(): Int {
        val houses = HouseMap()
        val santa = Santa()

        input.forEach { direction ->
            santa.move(direction)
            houses.visitHouse(santa.position)
        }

        return houses.amountOfVisitedHouses
    }

    fun countHousesWithRoboSanta(): Int {
        val houses = HouseMap()
        val normalSanta = Santa()
        val roboSanta = Santa()

        var moveRoboSanta = false

        input.forEach { direction ->
            val santa = if (moveRoboSanta) roboSanta else normalSanta
            moveRoboSanta = !moveRoboSanta
            santa.move(direction)
            houses.visitHouse(santa.position)
        }

        return houses.amountOfVisitedHouses
    }

    private class HouseMap {

        private val houses = mutableMapOf<Position, Int>()

        val amountOfVisitedHouses
            get() = houses.size

        init {
            houses[Position(0, 0)] = 1
        }

        fun visitHouse(position: Position) {
            val visits = houses[position] ?: 0
            houses[position] = visits + 1
        }
    }

    private class Santa {
        var position = Position(0, 0)
            private set

        fun move(direction: Char) {
            position = when (direction) {
                '^' -> position.goNorth()
                '>' -> position.goEast()
                'v' -> position.goSouth()
                '<' -> position.goWest()
                else -> error("direction must be one of ^, >, v, <.")
            }
        }
    }

    private data class Position(
            val x: Int,
            val y: Int
    ) {

        fun goNorth() = Position(x, y + 1)
        fun goSouth() = Position(x, y - 1)
        fun goWest() = Position(x - 1, y)
        fun goEast() = Position(x + 1, y)
    }
}