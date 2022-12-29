package aoc2015.day11

import aoc2015.prepare
import aoc2015.start

/**
 * Created by Hans Markwart on 31.01.2019.
 */

fun main(args: Array<String>) {
    prepare(
            { "first valid password is: ${Day11.firstValidPassword()}" },
            { "second valid password is: ${Day11.secondValidPassword()}" }
    ).start(args)
}