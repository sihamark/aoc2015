package aoc2015.day20

object Day20 {

    private const val TARGET_AMOUNT_OF_PRESENTS = 36000000

    fun findLowestHouseWithTargetAmount(): Int {
        var currentHouse = 1
        while (true) {
            val score = calculateScore(currentHouse)
            if (score >= TARGET_AMOUNT_OF_PRESENTS) {
                return currentHouse
            }
            if (currentHouse.rem(1_000) == 0) {
                println("house: $currentHouse: $score")
            }
            currentHouse++
        }
    }

    private fun calculateScore(houseI: Int): Int {
        if (houseI == 1) return 10

        return (2..houseI).asSequence()
                .filter { elf -> houseI.rem(elf) == 0 }
                .map { it * 10 }
                .sum() + 10
    }
}