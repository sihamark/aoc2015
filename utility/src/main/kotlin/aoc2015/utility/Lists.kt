package aoc2015.utility

fun <T> List<T>.allPermutations(maxElements: Int, onSolutionFound: (List<T>) -> Unit) {
    iteratePermutations(onSolutionFound, maxElements, listOf(), this)
}

private fun <T> iteratePermutations(
        onSolutionFound: (List<T>) -> Unit,
        maxElements: Int,
        already: List<T>,
        possibleElements: List<T>) {

    if (already.size >= maxElements) {
        onSolutionFound(already)
        return
    }
    for (left in possibleElements) {
        iteratePermutations(
                onSolutionFound,
                maxElements,
                already.toMutableList().apply { add(left) },
                possibleElements
        )
    }
}

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