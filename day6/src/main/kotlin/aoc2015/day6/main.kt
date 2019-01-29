package aoc2015.day6

import aoc2015.prepare
import aoc2015.start

/**
 * Created by Hans Markwart on 29.01.2019.
 */

fun main(args: Array<String>) {
    prepare(
            { "${Day6.calculateLitLights()} lights are lit" },
            { "the total brightness is ${Day6.calculateTotalBrightness()}" }
    ).start(args)
}
