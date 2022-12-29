package aoc2015.day20

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "the lowest house with at least the target amount of presents: ${Day20.findLowestHouseWithTargetAmount()}" },
            { "" }
    ).start(args)
}
