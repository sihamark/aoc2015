package aoc2015.day3

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "Santa visited ${Day3.countHouses()} houses" },
            { "Santa and Robo-Santa visited ${Day3.countHousesWithRoboSanta()} houses" }
    ).start(args)
}
