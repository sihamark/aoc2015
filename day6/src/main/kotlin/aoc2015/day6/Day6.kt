package aoc2015.day6

/**
 * Created by Hans Markwart on 29.01.2019.
 */
object Day6 {

    fun calculateLitLights(): Int {
        val matrix = ToggleLightMatrix()
        input.forEach { rawCommand ->
            val command = Parser.parse(rawCommand)
            command.positions.forEach { position ->
                when (command.type) {
                    Command.Type.TURN_ON -> matrix.turnOn(position)
                    Command.Type.TURN_OFF -> matrix.turnOff(position)
                    Command.Type.TOGGLE -> matrix.toggle(position)
                }
            }
        }
        return matrix.countTurnedOn()
    }

    fun calculateTotalBrightness(): Int {
        val matrix = BrightnessLightMatrix()
        input.forEach { rawCommand ->
            val command = Parser.parse(rawCommand)
            command.positions.forEach { position ->
                when (command.type) {
                    Command.Type.TURN_ON -> matrix.turnOn(position)
                    Command.Type.TURN_OFF -> matrix.turnOff(position)
                    Command.Type.TOGGLE -> matrix.toggle(position)
                }
            }
        }
        return matrix.countTurnedOn()
    }

    object Parser {
        private val regex = Regex("""(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)""")

        fun parse(rawCommand: String): Command {
            val matches = regex.find(rawCommand)?.groupValues ?: error("invalid raw command: $rawCommand")
            assert(matches.size == 6)

            val type = when (val rawType = matches[1]) {
                "turn on" -> Command.Type.TURN_ON
                "turn off" -> Command.Type.TURN_OFF
                "toggle" -> Command.Type.TOGGLE
                else -> error("unknown command: $rawType")
            }

            val start = parsePosition(matches[2], matches[3])
            val end = parsePosition(matches[4], matches[5])

            return Command(type, start..end)
        }

        private fun parsePosition(x: String, y: String) = Position(x.toInt(), y.toInt())
    }

    data class Command(
            val type: Type,
            val positions: PositionRange
    ) {
        enum class Type {
            TURN_ON, TURN_OFF, TOGGLE
        }
    }

    operator fun Position.rangeTo(other: Position) = PositionRange(this, other)

    data class PositionRange(
            val start: Position,
            val endInclusive: Position
    ) : Iterable<Position> {
        fun contains(position: Position) =
                position.x >= start.x && position.x <= endInclusive.x &&
                        position.y >= start.y && position.y <= endInclusive.y

        override fun iterator(): Iterator<Position> = PositionRangeIterator()

        private fun Position.next(): Position? {
            if (!contains(this)) return null
            if (this.x < endInclusive.x) return Position(this.x + 1, this.y)
            if (this.y < endInclusive.y) return Position(start.x, this.y + 1)
            return null
        }

        inner class PositionRangeIterator : Iterator<Position> {
            private var hasNext = true
            private var current = start

            override fun next(): Position {
                val result = current
                val next = current.next()
                if (next == null) {
                    hasNext = false
                    return result
                }
                current = next
                return result
            }

            override fun hasNext(): Boolean = hasNext
        }
    }

    data class Position(
            val x: Int,
            val y: Int
    ) : Comparable<Position> {

        val index: Int
            get() = y * MAX + x

        init {
            validRange.let { validRange ->
                if (!validRange.contains(x)) error("x")
                if (!validRange.contains(y)) error("y")
            }
        }

        override fun compareTo(other: Position) = index.compareTo(other.index)

        companion object {
            const val MIN = 0
            const val MAX = 1000
            private const val ERROR = "%s must be greater or equals than $MIN and lower than $MAX"

            val validRange = MIN until MAX

            private fun error(variable: String): Nothing = throw IllegalArgumentException(ERROR.format(variable))
        }
    }

    class BrightnessLightMatrix {
        private val lights = mutableMapOf<Position, Int>()

        private operator fun get(position: Position): Int = lights[position] ?: 0
        private operator fun set(position: Position, value: Int) {
            lights[position] = value
        }

        fun toggle(position: Position) {
            this[position] = this[position] + 2
        }

        fun turnOn(position: Position) {
            this[position] = this[position] + 1
        }

        fun turnOff(position: Position) {
            this[position] = (this[position] - 1).let { if (it < 0) 0 else it }
        }

        fun countTurnedOn() = lights.map { it.value }.sum()
    }

    class ToggleLightMatrix {
        private val lights = mutableMapOf<Position, Boolean>()

        private operator fun get(position: Position): Boolean = lights[position] ?: false
        private operator fun set(position: Position, value: Boolean) {
            lights[position] = value
        }

        fun toggle(position: Position) {
            this[position] = !this[position]
        }

        fun turnOn(position: Position) {
            this[position] = true
        }

        fun turnOff(position: Position) {
            this[position] = false
        }

        fun countTurnedOn() = lights.count { it.value }
    }
}