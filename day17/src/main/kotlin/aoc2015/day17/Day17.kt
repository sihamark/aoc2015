package aoc2015.day17

import aoc2015.utility.allVariants
import java.math.BigInteger

object Day17 {

    private const val TARGET_LITRES = 150

    fun findAmountOfTargetCombinations(): Int {
        val buckets = input.map { Parser.parse(it) }

        val maxVariants = buckets.size.factorial()
        println("max variants: $maxVariants")
        var correct = 0

        TODO()

        buckets.allVariants {
            println(it)
            val sum = it.map(Bucket::litre).sum()
            if (sum == TARGET_LITRES) correct++
        }

        return correct
    }

    private fun Int.factorial(): BigInteger {
        var factorial = BigInteger.ONE
        for (i in 1..this) {
            // factorial = factorial * i;
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        return factorial
    }

    private data class Bucket(val litre: Int) {
        override fun toString() = litre.toString()
    }

    private object Parser {
        fun parse(rawBucket: String) = Bucket(rawBucket.toInt())
    }
}