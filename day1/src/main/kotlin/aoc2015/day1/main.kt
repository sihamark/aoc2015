package aoc2015.day1

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "final floor is ${Day1.findFinalFloor()}" },
            { "first basement floor is ${Day1.findFirstBasementPosition()}" }
    ).start(args)
}
