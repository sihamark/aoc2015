package aoc2015.day14

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "the winning reindeer travelled ${Day14.calculateDistanceOfWinningReindeer()} km" },
            { "the winning reindeer scored ${Day14.calculatePointsOfBestReindeer()} points" }
    ).start(args)
}
