package aoc2015.day16

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "sue number ${Day16.findNumberOfSue()} send you the gift" },
            { "sue number ${Day16.findRealNumberOfSue()} send you the gift" }
    ).start(args)
}
