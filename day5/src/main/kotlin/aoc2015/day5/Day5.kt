package aoc2015.day5

/**
 * [https://adventofcode.com/2015/day/5]
 */
object Day5 {
    fun validatePart1() = countValid(Part1Validator())
    fun validatePart2() = countValid(Part2Validator()).also {
        assert(it == 53) { "there should be 53 valid string in part two" }
    }

    private fun countValid(validator: Validator): Int = input.filter { validator.validate(it) }.size

    interface Validator {
        fun validate(string: String): Boolean
    }

    class Part1Validator : Validator {
        private val prohibitedSubstrings = listOf("ab", "cd", "pq", "xy")
        private val vowels = listOf('a', 'e', 'i', 'o', 'u')

        override fun validate(string: String): Boolean =
                string.containsAtLeastThreeVowels()
                        && string.containsDoubleLetter()
                        && string.containsNoProhibitedString()

        private fun String.containsAtLeastThreeVowels() = filter { it in vowels }.length >= 3

        private fun String.containsDoubleLetter(): Boolean {
            var latestChar = first()
            drop(1).forEach {
                if (latestChar == it) return true
                latestChar = it
            }

            return false
        }

        private fun String.containsNoProhibitedString(): Boolean {
            prohibitedSubstrings.forEach {
                if (contains(it)) return false
            }
            return true
        }
    }

    class Part2Validator : Validator {

        override fun validate(string: String) =
                string.containsRepeatTuple()
                        && string.containsEnclosingRepeat()

        private fun String.containsRepeatTuple(): Boolean {
            val tuples = mutableSetOf<String>()
            var latestChar = this.first()
            this.drop(1).forEach {
                tuples += latestChar.toString() + it.toString()
                latestChar = it
            }

            val tupleCount = tuples.associateWith {
                this.countNotOverlapping(it)
            }

            return (tupleCount.any { it.value > 1 })
        }

        private fun String.countNotOverlapping(subString: String): Int {
            var counter = 0
            var workingString = this

            while (workingString.contains(subString)) {
                counter++
                workingString = workingString.replaceFirst(subString, "**")
            }
            return counter
        }

        private fun String.containsEnclosingRepeat(): Boolean {
            forEachIndexed { index, c ->
                if (c == this.getOrNull(index + 2)) return true
            }
            return false
        }
    }
}
