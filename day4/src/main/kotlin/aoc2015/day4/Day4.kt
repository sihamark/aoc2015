package aoc2015.day4

import java.math.BigInteger
import java.security.MessageDigest

/**
 * [https://adventofcode.com/2015/day/4]
 */
object Day4 {

    fun findHashIndexWith5LeadingZeros() = findHashIndex(5)

    fun findHashIndexWith6LeadingZeros() = findHashIndex(6, 282_749)

    private fun findHashIndex(leadingZeros: Int, startIndex: Int = 0): Int {
        var index = startIndex

        var key: String
        var hash: String
        do {
            index++
            key = input + index.toString()
            hash = key.md5()
        } while (!hash.hasLeadingZeros(leadingZeros))

        return index
    }

    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    private fun String.hasLeadingZeros(amount: Int) = take(amount)
            .all { it == '0' }
}