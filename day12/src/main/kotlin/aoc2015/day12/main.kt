package aoc2015.day12

import aoc2015.prepare
import aoc2015.start

/**
 * Created by Hans Markwart on 31.01.2019.
 */
fun main(args: Array<String>) {
    prepare(
            { "sum of all numbers is: ${Day12.sumAllNumbers()}" },
            { "sum of all numbers that are not red ${Day12.sumAllNotRedNumbers()}" }
    ).start(args)
}