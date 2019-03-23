package aoc2015.day18

class Grid(lightValues: Map<Position, Boolean> = mapOf()) {
    private val lights = lightValues.toMutableMap()

    private operator fun set(x: Int, y: Int, isOn: Boolean) {
        if (x !in rows) return
        if (y !in columns) return
        lights[Position(x, y)] = isOn
    }

    private operator fun set(position: Position, isOn: Boolean) {
        set(position.x, position.y, isOn)
    }

    operator fun get(x: Int, y: Int) = lights[Position(x, y)] ?: false

    operator fun get(position: Position) = lights[position] ?: false

    fun amountOfTurnedOnLights() = lights.values.count { it }

    override fun toString() = buildString {
        rows.forEach { x ->
            columns.forEach { y ->
                append(if (this@Grid[x, y]) '#' else '.')
            }
            append('\n')
        }
    }

    operator fun inc(): Grid {
        val nextGrid = Grid()
        this.forEach { position, isOn ->
            val adjacentOn = adjacent(position)
                    .map { this[it] }
                    .count { it }
            nextGrid[position] = if (isOn) {
                adjacentOn == 2 || adjacentOn == 3
            } else {
                adjacentOn == 3
            }
        }
        return nextGrid
    }

    private fun forEach(block: (position: Position, isOn: Boolean) -> Unit) {
        positions.forEach {
            block(it, this[it])
        }
    }

    fun turnOnCorners() {
        corners().forEach { lights[it] = true }
    }

    companion object {
        const val MAX_WIDTH = 100
        const val MAX_HEIGHT = 100

        val positions by lazy {
            rows.flatMap { x ->
                columns.map { y ->
                    Position(x, y)
                }
            }
        }

        val rows by lazy { (0 until MAX_WIDTH) }
        val columns by lazy { (0 until MAX_HEIGHT) }

        private fun adjacent(position: Position): List<Position> = with(position) {
            listOf(
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

        private fun corners() = listOf(
                Position(0, 0),
                Position(0, 99),
                Position(99, 0),
                Position(99, 99))
    }
}