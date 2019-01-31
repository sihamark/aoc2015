package aoc2015.day12

import com.squareup.moshi.Moshi

/**
 * Created by Hans Markwart on 31.01.2019.
 */
object Day12 {

    fun sumAllNumbers(): Int = sum(parsedInput())
    fun sumAllNotRedNumbers(): Int = sumNotRed(parsedInput())

    private fun parsedInput() = Moshi.Builder()
            .build()
            .adapter(List::class.java)
            .fromJson(input) ?: error("shit just hit the fan")

    private fun sum(any: Any): Int = when (any) {
        is Number -> any.toInt()
        is List<*> -> any.sumBy { sum(it ?: 0) }
        is Map<*, *> -> any.entries.sumBy { sum(it.key ?: 0) + sum(it.value ?: 0) }
        else -> 0
    }

    private fun sumNotRed(any: Any): Int = when (any) {
        is Number -> any.toInt()
        is List<*> -> any.sumBy { sumNotRed(it ?: 0) }
        is Map<*, *> -> {
            if (any.values.any { it == "red" }) {
                0
            } else {
                any.entries.sumBy { sumNotRed(it.key ?: 0) + sumNotRed(it.value ?: 0) }
            }
        }
        else -> 0
    }

}