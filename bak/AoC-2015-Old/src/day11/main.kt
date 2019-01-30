package day11

const val input = "hxbxwxba"
val validChars = ('a'..'z').toList()

fun Char.indexed() = validChars.indexOf(this)

fun main() {
    doForReal()
}

fun doForReal() {
    val firstValid = input.nextValidPassword()
    val secondValid = firstValid.increment().nextValidPassword()

    println("$input -> $firstValid -> $secondValid")
}

fun testWithUserInput() {
    var current = input
    while (true) {
        println("$current is valid? ${current.isValid()}")
        current = current.increment()
        val abort = readLine() == "e"
        if (abort) break
    }
}

fun String.nextValidPassword(): String {
    var current = this
    while (!current.isValid()) {
        current = current.increment()
    }
    return current
}

fun String.isValid() = !PasswordValidator.containsIllegal(this)
        && PasswordValidator.containsStraightIncreasing(this)
        && PasswordValidator.containsDoublettes(this)

fun String.increment() = PasswordIncrementer.increment(this)

object PasswordIncrementer {

    fun increment(password: String): String = increment(password, password.length - 1)

    private fun increment(password: String, index: Int): String {
        val oldChar = password[index]
        val newCharIndex = validChars.indexOf(oldChar) + 1
        if (newCharIndex < validChars.size) {
            return password.replaceAt(index, validChars[newCharIndex])
        }

        val adjustedCharIndex = newCharIndex.rem(validChars.size)
                .let { if (it >= 0) it else validChars.size + it }

        if (index == 0) {
            return password.replaceAt(index, validChars[adjustedCharIndex])
        }

        return increment(password.replaceAt(index, validChars[adjustedCharIndex]), index - 1)
    }

    private fun String.replaceAt(index: Int, char: Char) =
            replaceRange(index, index + 1, char.toString())
}

object PasswordValidator {

    fun containsStraightIncreasing(password: String): Boolean {
        password.forEachIndexed { i, c ->
            val currentCharIndex = c.indexed()
            if (
                    password.getOrNull(i + 1)?.indexed() == currentCharIndex + 1 &&
                    password.getOrNull(i + 2)?.indexed() == currentCharIndex + 2
            ) {
                return true
            }
        }
        return false
    }

    fun containsIllegal(password: String) = setOf('i', 'o', 'l')
            .map { password.contains(it) }
            .reduce { b, acc -> acc || b }

    fun containsDoublettes(password: String): Boolean {
        val doublettes = mutableSetOf<Char>()
        password.forEachIndexed { index, c ->
            if (c == password.getOrNull(index + 1)) {
                doublettes += c
            }
        }
        return doublettes.size >= 2
    }
}