package aoc2015.day1

object Day1 {

    fun findFinalFloor() = input.count { it == '(' } - input.count { it == ')' }

    fun findFirstBasementPosition(): Int {
        var floor = 0
        input.forEachIndexed { index, item ->
            when (item) {
                '(' -> floor++
                ')' -> floor--
            }
            if (floor < 0) {
                return index + 1
            }
        }

        return 0
    }
}