package aoc2015.day2

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "needed ${Day2.calculateNeededSurface()} square feet of wrapping paper" },
            { "needed ${Day2.calculateNeededRibbon()} feet of ribbon" }
    ).start(args)
}
