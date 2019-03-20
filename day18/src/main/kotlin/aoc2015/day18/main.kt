package aoc2015.day18

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "after 100 steps ${Day18.amountOfLightsTurnedOnAfter100Steps()} lights are on" },
            { "after 100 steps with the corners always on ${Day18.amountOfLightsTurnedOnAfter100StepsAndOnCorners()} lights are on" }
    ).start(args)
}
