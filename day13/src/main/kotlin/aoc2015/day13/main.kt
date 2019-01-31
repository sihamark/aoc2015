package aoc2015.day13

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "highest happiness without you: ${Day13.calculateHighestEffectWithoutYou()}" },
            { "highest happiness with you: ${Day13.calculateHighestEffectWithYou()}" }
    ).start(args)
}

