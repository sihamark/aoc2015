package aoc2015.day19

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
    prepare(
            { "There are ${Day19.numberOfDistinctMoleculesAfterOneReplacement()} distinct molecules after one replacement" },
            { "You need at least ${Day19.findFewestStepsToFabricateMedicine()} steps to fabricate the medicine" }
    ).start(args)
}
