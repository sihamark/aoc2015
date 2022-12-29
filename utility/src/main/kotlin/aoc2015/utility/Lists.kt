package aoc2015.utility

/**
 * finds all permutations of the receiver list.
 * Each permutation has the same length of the receiver list.
 *
 * For example, when applying this function in the list (1, 2, 3) = (1, 2, 3), (1, 3, 2), (2, 1, 3), (2, 3, 1), (3, 1, 2), (3, 2, 1)
 */
fun <T> List<T>.allPermutations(): List<List<T>> {
    val solutions = mutableListOf<List<T>>()

    fun iteratePermutations(already: List<T>, leftElements: List<T>) {
        if (leftElements.isEmpty()) {
            solutions.add(already)
            return
        }
        for (left in leftElements) {
            iteratePermutations(
                    already.toMutableList().apply { add(left) },
                    leftElements.toMutableList().apply { remove(left) }
            )
        }
    }

    iteratePermutations(listOf(), this)
    return solutions
}

/**
 * finds all variants of the receiver list.
 *
 * For example, when applying this function in the list (1, 2, 3) = (), (1), (1, 2), (1, 2, 3), (1, 3), (1, 3, 2), (2), (2, 1), (2, 1, 3), (2, 3), (2, 3, 1), (3), (3, 1), (3, 1, 2), (3, 2), (3, 2, 1)
 */
fun <T> List<T>.allVariants(onSolution: (List<T>) -> Unit) {
    fun iterateVariants(already: List<T>, leftElements: List<T>) {
        onSolution(already)
        for (left in leftElements) {
            iterateVariants(
                    already.toMutableList().apply { add(left) },
                    leftElements.toMutableList().apply { remove(left) }
            )
        }
    }

    iterateVariants(listOf(), this)
}

/**
 * finds all variants of the receiver list.
 *
 * For example, when applying this function in the list (1, 2, 3) = (), (1), (1, 2), (1, 2, 3), (1, 3), (1, 3, 2), (2), (2, 1), (2, 1, 3), (2, 3), (2, 3, 1), (3), (3, 1), (3, 1, 2), (3, 2), (3, 2, 1)
 */
fun <T> List<T>.allVariants(): List<List<T>> {
    val solutions = mutableListOf<List<T>>()
    allVariants { solutions.add(it) }
    return solutions
}



