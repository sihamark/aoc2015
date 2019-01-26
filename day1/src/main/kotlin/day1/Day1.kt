package day1

object Day1 {

    fun findFinalFloor() {
        val finalFloor = input.count { it == '(' } - input.count { it == ')' }
        println("final floor $finalFloor")
    }

    fun findFirstBasementPosition() {
        var floor = 0
        input.forEachIndexed { index, item ->
            when (item) {
                '(' -> floor++
                ')' -> floor--
            }
            if (floor < 0) {
                print("first basement at position ${index + 1}")
                return
            }
        }
    }
}