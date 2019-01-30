package aoc2015.utility

fun <T> List<T>.allPermutations(): List<List<T>> {
    val solutions = mutableListOf<List<T>>()
    iteratePermutations(solutions, listOf(), this)
    return solutions
}

private fun <T> iteratePermutations(solutions: MutableList<List<T>>, already: List<T>, leftElements: List<T>) {
    if (leftElements.isEmpty()) {
        solutions.add(already)
        return
    }
    for (left in leftElements) {
        iteratePermutations(
                solutions,
                already.toMutableList().apply { add(left) },
                leftElements.toMutableList().apply { remove(left) }
        )
    }
}