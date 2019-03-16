package aoc2015.day17

object Day17 {

    private const val TARGET_LITRES = 150

    fun findAmountOfTargetCombinations(): Int {
        val buckets = input.map { Parser.parse(it) }
        val factors = Factors(buckets.size)
        var correct = 0

        while (!factors.isLast) {
            val sumLitres = buckets * factors
            if (sumLitres == TARGET_LITRES) correct++

            factors.increase()
        }

        return correct
    }

    fun findAmountOfTargetCombinationsWithMinimalBuckets(): Int {
        val buckets = input.map { Parser.parse(it) }
        val factors = Factors(buckets.size)
        var correct = 0

        while (!factors.isLast) {
            val sumLitres = buckets * factors
            if (sumLitres == TARGET_LITRES) correct++

            factors.increase()
        }

        return correct
    }

    private operator fun List<Bucket>.times(factors: Factors): Int {
        if (size != factors.size) error("size of buckets and factors must be equal")

        return mapIndexed { index, bucket -> bucket.litre * factors[index] }
                .sum()
    }

    private class Factors(size: Int) {
        private val factors = IntArray(size) { 0 }

        val size
            get() = factors.size

        val sizeOfActive
            get() = factors.count { it == 1 }

        val isLast
            get() = factors.all { it == 1 }

        operator fun get(index: Int): Int = factors[index]

        fun increase() {
            increaseAtIndex(factors.lastIndex)
        }

        private fun increaseAtIndex(index: Int) {
            factors[index]++
            if (factors[index] == 2) {
                factors[index] = 0
                if (index == 0) return
                increaseAtIndex(index - 1)
            }
        }

        override fun toString() = factors.joinToString(separator = "")
    }

    private data class Bucket(val litre: Int) {
        override fun toString() = litre.toString()
    }

    private object Parser {
        fun parse(rawBucket: String) = Bucket(rawBucket.toInt())
    }
}