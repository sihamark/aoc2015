package aoc2015.day14

/**
 * Created by Hans Markwart on 31.01.2019.
 */
object Day14 {

    private const val RACE_DURATION = 2503

    fun calculateDistanceOfWinningReindeer(): Int {
        val reindeers = input.map { Parser.parse(it) }
        return Race(reindeers, RACE_DURATION).calculateFarthestDistance()
    }

    fun calculatePointsOfBestReindeer(): Int {
        val reindeers = input.map { Parser.parse(it) }
        return Race(reindeers, RACE_DURATION).calculateMaximalPoints()
    }

    private data class Reindeer(
            val name: String,
            val speed: Int,
            val speedingDuration: Int,
            val restDuration: Int
    ) {
        val cycleDuration = speedingDuration + restDuration
        val cycleDistance = speed * speedingDuration

        fun distanceAfter(duration: Int): Int {
            val completeCycles = duration / cycleDuration
            val completeCycleDuration = completeCycles * cycleDuration
            val completeCycleDistance = completeCycles * cycleDistance

            val remainingTime = duration - completeCycleDuration

            val lastCycleSpeedingDuration = if (remainingTime > speedingDuration) speedingDuration else remainingTime

            return completeCycleDistance + speed * lastCycleSpeedingDuration
        }
    }

    private object Parser {
        private val validDescription = Regex("""(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.""")

        fun parse(rawReindeer: String): Reindeer {
            val parsedValues = validDescription.find(rawReindeer)?.groupValues
                    ?: error("invalid raw reindeer: $rawReindeer")

            return Reindeer(
                    parsedValues[1],
                    parsedValues[2].toInt(),
                    parsedValues[3].toInt(),
                    parsedValues[4].toInt()
            )
        }
    }

    private class Race(
            private val reindeers: List<Reindeer>,
            private val duration: Int
    ) {

        init {
            if (reindeers.isEmpty()) error("must input at least one reindeer")
        }

        fun calculateFarthestDistance(): Int = reindeers
                .map { it.distanceAfter(duration) }
                .max()!!

        fun calculateMaximalPoints(): Int {
            val points = reindeers.associateWith { 0 }.toMutableMap()

            (1..duration).forEach { passedSeconds ->
                val reindeersDistance = reindeers.associateWith { it.distanceAfter(passedSeconds) }
                val max = reindeersDistance.maxBy { it.value }!!.value

                reindeersDistance.filterValues { it == max }
                        .forEach { (reindeer, _) -> points[reindeer] = points[reindeer]!! + 1 }
            }

            return points.values.max()!!
        }
    }
}