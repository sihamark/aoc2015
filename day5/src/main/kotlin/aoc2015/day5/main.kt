package aoc2015.day5

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "found ${Day5.validatePart1()} nice strings" },
            { "found ${Day5.validatePart2()} nice strings" }
    ).start(args)
}
