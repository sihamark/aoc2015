package aoc2015.day15

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "highest cookie score is ${Day15.highestCookieScore()}" },
            { "highest cookie score with 500 calories us ${Day15.highestCookieScoreWith500Calories()}" }
    ).start(args)
}
