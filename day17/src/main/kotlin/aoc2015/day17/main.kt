package aoc2015.day17

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "amount of configurations with 150 litres: ${Day17.findAmountOfTargetCombinations()}" },
            { "" }
    ).start(args)
}
