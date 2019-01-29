package aoc2015.day4

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "lowest number to produce a hash with 5 leading zeros is ${Day4.findHashIndexWith5LeadingZeros()}" },
            { "lowest number to produce a hash with 6 leading zeros is ${Day4.findHashIndexWith6LeadingZeros()}" }
    ).start(args)
}
