package aoc2015.day17

object Day17 {

    private const val TARGET_LITRES = 150

    fun findAmountOfTargetCombinations(): Int {
        val buckets = input.map { Parser.parse(it) }
        val factors = Factors(buckets.size)

        return factors.asSequence()
                .map { buckets * it }
                .filter { it == TARGET_LITRES }
                .count()
    }

    fun findAmountOfTargetCombinationsWithMinimalBuckets(): Int {
        val buckets = input.map { Parser.parse(it) }
        val factors = Factors(buckets.size)

        val minBuckets = factors.asSequence()
                .map { it to (buckets * it) }
                .filter { (_, litres) -> litres == TARGET_LITRES }
                .minBy { factors.sizeOfActive }!!.first.sizeOfActive

        //441 is not correct
        return factors.asSequence()
                .filter { it.sizeOfActive == minBuckets }
                .map { buckets * it }
                .filter { it == TARGET_LITRES }
                .count()
    }

    private operator fun List<Bucket>.times(factors: Factors): Int {
        if (size != factors.size) error("size of buckets and factors must be equal")

        return mapIndexed { index, bucket -> bucket.litre * factors[index] }
                .sum()
    }

    private class Factors(
            private val factors: IntArray
    ) : Iterable<Factors> {

        constructor(size: Int) : this(IntArray(size) { 0 })

        val size
            get() = factors.size

        val sizeOfActive
            get() = factors.count { it == 1 }

        val isLast
            get() = factors.all { it == 1 }

        operator fun get(index: Int): Int = factors[index]

        operator fun inc() = copy().apply { increase() }

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

        fun copy() = Factors(factors.copyOf())

        override fun iterator() = FactorsIterator()

        override fun toString() = factors.joinToString(separator = "")

        inner class FactorsIterator : Iterator<Factors> {
            private var current = this@Factors

            override fun hasNext() = !current.isLast

            override fun next(): Factors {
                val result = current
                current++
                return result
            }

        }
    }

    private data class Bucket(val litre: Int) {
        override fun toString() = litre.toString()
    }

    private object Parser {
        fun parse(rawBucket: String) = Bucket(rawBucket.toInt())
    }
}