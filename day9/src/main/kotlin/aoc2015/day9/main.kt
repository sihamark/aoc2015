package aoc2015.day9

import aoc2015.prepare
import aoc2015.start

/**
 * Created by Hans Markwart on 29.01.2019.
 */

fun main(args: Array<String>) {
    prepare(
            { "shortest distance is ${Day9.findShortestRoute()}" },
            { "longest distance is ${Day9.findLongestRoute()}" }
    ).start(args)
}