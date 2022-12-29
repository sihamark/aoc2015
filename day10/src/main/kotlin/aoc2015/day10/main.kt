package aoc2015.day10

import aoc2015.prepare
import aoc2015.start

/**
 * Created by Hans Markwart on 31.01.2019.
 */
fun main(args: Array<String>) {
    prepare(
            { "the length of the string after looking-and-saying 40 times is ${Day10.lengthOfLookAndSayAfter40Times()}" },
            { "the length of the string after looking-and-saying 50 times is ${Day10.lengthOfLookAndSayAfter50Times()}" }
    ).start(args)
}