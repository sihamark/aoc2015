package aoc2015.day7

import aoc2015.prepare
import aoc2015.start

/**
 * Created by Hans Markwart on 29.01.2019.
 */

@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    prepare(
            { "value on wire a is ${Day7.valueForWireA()}" },
            { "value on wire b after a is resolved ${Day7.valueForWireBAfterA()}" }
    ).start(args)
}